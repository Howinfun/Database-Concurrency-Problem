package com.hyf.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Howinfun
 * @desc
 * @date 2019/9/2
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 名称 */
    private String bookName;
    /** 阅读量 */
    private Integer readFrequency;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
    /** 版本号 */
    private Integer version;


}
