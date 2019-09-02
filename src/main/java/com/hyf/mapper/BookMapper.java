package com.hyf.mapper;

import com.hyf.entity.Book;
import com.hyf.query.BookQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author Howinfun
 * @desc
 * @date 2019/9/2
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

    /**
     * 根据书名获取版本号
     * @param bookName
     * @return
     */
    @Select("select version from book where book_name = #{bookName}")
    Integer getVersionByBookName(@Param("bookName") String bookName);

    /**
     * 根据书名和版本号更新book
     * @param book
     * @return
     */
    @Update("update book set version = version+1,read_frequency = read_frequency+1 where book_name = #{bookName} and version = #{version}")
    Integer updateBookByVersion(BookQuery book);
}
