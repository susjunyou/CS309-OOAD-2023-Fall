package com.example.ooad_project_backend.controller;


import cn.hutool.core.util.ObjectUtil;
import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.common.ResultCode;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.exception.CustomException;
import com.example.ooad_project_backend.service.TeacherInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Result findAll() {
        List<TeacherInfo> List = teacherInfoService.findALl();
        return Result.success(List);
    }

    @PutMapping
    public Result update(@RequestBody TeacherInfo teacherInfo) {
        System.out.println(teacherInfo.toString());
        teacherInfoService.update(teacherInfo);

        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        teacherInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<TeacherInfo> pageInfo = teacherInfoService.findPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }
    @GetMapping("/{search}")
    public Result findPageSearch(@PathVariable String search, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<TeacherInfo> pageInfo = teacherInfoService.findPageSearch(search, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
