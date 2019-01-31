package com.tongjingcheng.mybatis.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tongjingcheng
 * @since 2019-01-31 10:45
 */
@Data
public class StudentVo implements Serializable {

    private String name;
    private int sex;
}
