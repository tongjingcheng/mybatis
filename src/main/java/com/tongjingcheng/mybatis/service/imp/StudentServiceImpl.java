package com.tongjingcheng.mybatis.service.imp;

import com.tongjingcheng.mybatis.entity.JoinSearch;
import com.tongjingcheng.mybatis.entity.Student;
import com.tongjingcheng.mybatis.mapper.StudentMapper;
import com.tongjingcheng.mybatis.response.StudentVo;
import com.tongjingcheng.mybatis.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tongjingcheng
 * @since 2019-01-30 17:34
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private  StudentMapper studentMapper;

    @Override
    @Transactional
    public List<StudentVo> studentList() {
         studentMapper.studentList();//调用两次，看有没有cache哈哈
       List<Student> list  =studentMapper.studentList();
        if(list == null){
            return null;
        }
        List<StudentVo> newList = new ArrayList<>();
        for (Student stu:list){
            StudentVo newStudentVo = new StudentVo();
            BeanUtils.copyProperties(stu,newStudentVo);
            newList.add(newStudentVo);

        }
        return newList;
    }

    @Override
    public List<JoinSearch> joinSearch() {
        return studentMapper.joinSearch();
    }

}
