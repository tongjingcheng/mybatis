package com.tongjingcheng.mybatis.mapper;

import com.tongjingcheng.mybatis.entity.JoinSearch;
import com.tongjingcheng.mybatis.entity.Student;

import java.util.List;

/**
 * 学生操作
 */
public interface StudentMapper {

    List<Student> studentList();

    List<JoinSearch> joinSearch();

}
