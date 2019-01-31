package com.tongjingcheng.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author tongjingcheng
 * @since 2019-01-30 16:14
 */
@Data
public class Student {

    @JsonIgnore //ignore field
    private int id;
    private String name;
    private int sex;
}
