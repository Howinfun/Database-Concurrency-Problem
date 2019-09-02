package com.hyf.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.hyf.entity.Book;
import com.hyf.mapper.BookMapper;
import com.hyf.query.BookQuery;
import com.hyf.service.BookService;
import com.hyf.utils.RedissonUtil;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2019/9/2
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Boolean addOrUpdateBook(BookQuery query) {
        boolean flag = true;
        // 使用双重检查锁定来处理新增的并发问题
        Book b = Book.builder().bookName(query.getBookName()).build();
        Book oldBook = this.bookMapper.selectOne(b);
        if (oldBook == null){
            // 加锁
            synchronized (this){
                oldBook = this.bookMapper.selectOne(b);
                if (oldBook == null){
                    BeanUtil.copyProperties(query,b);
                    this.bookMapper.insertSelective(b);
                }else{
                    updateBook(query);
                }
            }
            RLock lock = RedissonUtil.INSTANCE.getLock("addOrUpdateBook");
            try {
                // 尝试获取锁，最多等待10000毫秒，获取锁后1000毫秒自动释放锁
                if (lock.tryLock(10000, 10000, TimeUnit.MILLISECONDS)){
                    oldBook = this.bookMapper.selectOne(b);
                    if (oldBook == null){
                        BeanUtil.copyProperties(query,b);
                        this.bookMapper.insertSelective(b);
                    }else{
                        updateBook(query);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }finally {
                // 最后记得释放锁
                lock.unlock();
            }
        }else{
            updateBook(query);
        }
        return flag;
    }

    /**
     * 参考CAS的无锁算法来处理更新的并发问题（利用死循环+版本号）
     * @param query
     */
    private void updateBook(BookQuery query){
        // 参考cas
        for (;;){
            Integer version = this.bookMapper.getVersionByBookName(query.getBookName());
            query.setVersion(version);
            Integer updateCount = this.bookMapper.updateBookByVersion(query);
            if (updateCount != null && updateCount.equals(1)){
                break;
            }
        }
    }
}
