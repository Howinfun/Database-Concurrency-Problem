package com.hyf.controller;

import com.hyf.common.Result;
import com.hyf.query.BookQuery;
import com.hyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Howinfun
 * @desc
 * @date 2019/9/2
 */
@RequestMapping("/book")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/addOrUpdateBook",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result addOrUpdateBook(@RequestBody BookQuery query) {
        Result result = new Result();
        try {
            this.bookService.addOrUpdateBook(query);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(1);
            result.setMsg("新增或修改失败");
            return result;
        }
        result.setCode(0);
        result.setMsg("新增或修改成功");
        return result;
    }
}
