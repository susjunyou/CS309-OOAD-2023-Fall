package com.example.ooad_project_backend.controller;


import cn.hutool.core.util.ObjectUtil;
import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@SuppressWarnings("ALL")
@RestController
@RequestMapping("/admin")
@CrossOrigin
//首先私以为这个controller层的方法并不用严格的只让admin使用，这个controller只是包含较为底层且权限较高的方法
public class AdminController {
    @Autowired
    private CourseInfoService courseInfoService;

    @GetMapping("/addStudent")
    public Result addStudent(Integer courseId, Integer studentId) {
        if (ObjectUtil.isEmpty(courseId) || ObjectUtil.isEmpty(studentId)) {
            return Result.error("-1", "No such course or student");
        } else {
            courseInfoService.addStudent(courseId, studentId);
            return Result.success();
        }
    }

    @GetMapping("/addSA")
    public Result addSA(Integer courseId, Integer studentId) {
        if (ObjectUtil.isEmpty(courseId) || ObjectUtil.isEmpty(studentId)) {
            return Result.error("-1", "No such course or student");
        } else {
            courseInfoService.addSA(courseId, studentId);
            return Result.success();
        }
    }

    @GetMapping("/addTeacher")
    public Result addTeacher(Integer courseId, Integer teacherId) {
        if (ObjectUtil.isEmpty(courseId) || ObjectUtil.isEmpty(teacherId)) {
            return Result.error("-1", "No such course or teacher");
        } else {
            courseInfoService.addTeacher(courseId, teacherId);
            return Result.success();
        }
    }

    @GetMapping("/deleteStudent")
    public Result deleteStudent(Integer courseId, Integer studentId) {
        if (ObjectUtil.isEmpty(courseId) || ObjectUtil.isEmpty(studentId)) {
            return Result.error("-1", "No such course or student");
        } else {
            courseInfoService.deleteStudent(courseId, studentId);
            return Result.success();
        }
    }

    @GetMapping("/deleteSA")
    public Result deleteSA(Integer courseId, Integer studentId) {
        if (ObjectUtil.isEmpty(courseId) || ObjectUtil.isEmpty(studentId)) {
            return Result.error("-1", "No such course or student");
        } else {
            courseInfoService.deleteSA(courseId, studentId);
            return Result.success();
        }
    }

    @GetMapping("/deleteTeacher")
    public Result deleteTeacher(Integer courseId, Integer teacherId) {
        if (ObjectUtil.isEmpty(courseId) || ObjectUtil.isEmpty(teacherId)) {
            return Result.error("-1", "No such course or teacher");
        } else {
            courseInfoService.deleteTeacher(courseId, teacherId);
            return Result.success();
        }
    }

    @GetMapping("releaseCourse")
    public Result releaseCourse(String courseCode, String courseName, String courseDescription) {
        if (ObjectUtil.isEmpty(courseCode) || ObjectUtil.isEmpty(courseName) || ObjectUtil.isEmpty(courseDescription)) {
            return Result.error("-1", "No such course or teacher");
        } else {
            courseInfoService.releaseCourse(courseCode, courseName, courseDescription);
            return Result.success();
        }
    }

    @GetMapping("deleteCourse")
    public Result deleteCourse(Integer courseId) {
        if (ObjectUtil.isEmpty(courseId)) {
            return Result.error("-1", "No such course");
        } else {
            courseInfoService.deleteCourse(courseId);
            return Result.success();
        }
    }

    @GetMapping("/updateCourse")
    public Result updateCourse(Integer courseId, String courseCode, String courseName, String courseDescription) {
        if (ObjectUtil.isEmpty(courseId) || ObjectUtil.isEmpty(courseCode) || ObjectUtil.isEmpty(courseName) || ObjectUtil.isEmpty(courseDescription)) {
            return Result.error("-1", "No such course");
        } else {
            courseInfoService.updateCourse(courseId, courseCode, courseName, courseDescription);
            return Result.success();
        }
    }
}

