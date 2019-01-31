package com.tongjingcheng.mybatis.controller;

import com.tongjingcheng.mybatis.entity.JoinSearch;
import com.tongjingcheng.mybatis.response.StudentVo;
import com.tongjingcheng.mybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author tongjingcheng
 * @since 2019-01-29 14:39
 */
@Controller
public class HomeController {

    @Autowired
    private StudentService studentService;


    @Autowired
    private DataSource dataSource;

    @RequestMapping(value="index")
    public String index(Model model){
        model.addAttribute("name","alipay");
        Map<String,String> map = new HashMap<>();
        map.put("tong","童京城");
        map.put("url","http://www.tongjingcheng.cn");
        map.put("test","test");
        model.addAllAttributes(map);
        return "index";
    }

    @RequestMapping(value="datasource")
    @ResponseBody
    public String hello(){
        return "正在使用的数据源："+dataSource.getClass();
    }

    @RequestMapping(value="test")
    @ResponseBody
    public List<StudentVo> test() {
        return studentService.studentList();
    }

    @RequestMapping(value="jonsearch")
    @ResponseBody
    public List<JoinSearch> jonsearch() {
        return studentService.joinSearch();
    }
}
