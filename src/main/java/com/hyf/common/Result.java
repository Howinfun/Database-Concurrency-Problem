package com.hyf.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Howinfun
 * @desc
 * @date 2019/9/2
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
}
