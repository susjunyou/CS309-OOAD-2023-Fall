package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.service.CourseInfoService;
import com.example.ooad_project_backend.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/SA")
@CrossOrigin
//在这里有addSA，deleteSA，方法，只能让管理员和老师调用
public class SAController {
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private CourseInfoService courseInfoService;

    @GetMapping("/getMySACourseId")
    public Result getMySACourse(Integer studentId) {
        List<Integer> courseIdList = courseInfoService.findMySACourseIdByStudentId(studentId);
        if (courseIdList != null) {
            return Result.success(courseIdList);
        } else {
            return Result.error("1", "n");
        }
    }


    // 这个和 courseController 的 getAllSA 里面的重复了吧
    //是的QAQ
    @GetMapping("/getAllSAIdByCourseId")
    public Result getAllSAIdByCourseId(Integer courseId) {
        List<Integer> studentIdList = courseInfoService.findMySAIdByCourseId(courseId);
        if (studentIdList != null) {
            return Result.success(studentIdList);
        } else {
            return Result.error("1", "n");
        }
    }
}

