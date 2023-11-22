package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.*;
import com.example.ooad_project_backend.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseInfoService courseInfoService;

    @GetMapping("/projects")
    public Result getProjectsByCourseId(Integer courseId) {
        List<ProjectInfo> projectInfos = courseInfoService.findProjectInfoByCourseId(courseId);
        if (projectInfos.size() == 0) {
            return Result.error("1", "Maybe No such course");
        } else {
            return Result.success(projectInfos);
        }
    }

    @GetMapping("/assignments")
    public Result getAssignmentsByCourseId(Integer courseId) {
        List<AssignmentInfo> assignmentInfos = courseInfoService.findAssignmentInfoByCourseId(courseId);
        if (assignmentInfos.size() == 0) {
            return Result.error("1", "Maybe No such course");
        } else {
            return Result.success(assignmentInfos);
        }
    }

    @GetMapping("/posts")
    public Result getPostsByCourseId(Integer courseId) {
        List<PostInfo> postInfos = courseInfoService.findPostInfoByCourseId(courseId);
        if (postInfos.size() == 0) {
            return Result.error("1", "Maybe No such course");
        } else {
            return Result.success(postInfos);
        }
    }

    @GetMapping("/materials")
    public Result getMaterialsByCourseId(Integer courseId) {
        List<MaterialInfo> materialInfos = courseInfoService.findMaterialInfoByCourseId(courseId);
        if (materialInfos.size() == 0) {
            return Result.error("1", "Maybe No such course");
        } else {
            return Result.success(materialInfos);
        }
    }

    @GetMapping("/attendances")
    public Result getAttendancesByCourseId(Integer courseId) {
        List<AttendanceInfo> attendanceInfos = courseInfoService.findAttendanceInfoByCourseId(courseId);
        if (attendanceInfos.size() == 0) {
            return Result.error("1", "Maybe No such course");
        } else {
            return Result.success(attendanceInfos);
        }
    }

}
