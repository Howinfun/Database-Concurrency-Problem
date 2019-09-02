package com.hyf.service;

import com.hyf.query.BookQuery;

/**
 * @author Howinfun
 * @desc
 * @date 2019/9/2
 */
public interface BookService {

    /**
     * 新增修改Book
     * @param query
     * @return
     */
    Boolean addOrUpdateBook(BookQuery query);
}
