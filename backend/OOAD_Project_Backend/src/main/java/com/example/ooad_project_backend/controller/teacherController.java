package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@CrossOrigin
public class teacherController {

    @Autowired
    private TeacherInfoService teacherInfoService;

    @RequestMapping("/getCourseInfo")
    public Result getCoursesByTeacherId(Integer teacherId) {
        List<CourseInfo> courseInfoList = teacherInfoService.findCourseInfoByTeacherId(teacherId);
        if (courseInfoList == null) {
            return Result.error("1", "没有课程");
        } else {
            return Result.success(courseInfoList);
        }
    }
}
