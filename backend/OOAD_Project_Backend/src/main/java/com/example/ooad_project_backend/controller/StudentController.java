package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentInfoService studentInfoService;

    @GetMapping("/getCourseInfo")
    public Result getCoursesByStudentId(Integer studentId) {
        List<CourseInfo> courseInfoList = studentInfoService.findCourseInfoByStudentId(studentId);
        if (courseInfoList!= null) {
            return Result.success(courseInfoList);
        }else {
            return Result.error("1","该学生没有课程");
        }

    }
}
