package com.tongjingcheng.mybatis.service;

import com.tongjingcheng.mybatis.entity.JoinSearch;
import com.tongjingcheng.mybatis.response.StudentVo;

import java.util.List;

/**
 * @author tongjingcheng
 * @since 2019-01-30 17:33
 */
public interface StudentService {

    List<StudentVo> studentList();
    List<JoinSearch>  joinSearch();
}
