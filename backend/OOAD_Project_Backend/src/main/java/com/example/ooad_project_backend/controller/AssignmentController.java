package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.AssignmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

//@SuppressWarnings("ALL")
@RestController
@RequestMapping("/assignment")
@CrossOrigin
public class AssignmentController {
    //SA老师增删改查某门课的所有作业，推荐之后的方法都在这里写，不然好难找T.T
//如果找不到某个方法大概率写在GradeController里面了
    @Autowired
    private AssignmentInfoService assignmentInfoService;

    //提交作业部分在StudentController里面


    @GetMapping("/getAssignmentInfoByCourseId")
    public Result getAssignmentInfoByCourseId(Integer courseId) {
        List<AssignmentInfo> assignmentInfoList = assignmentInfoService.findAssignmentInfoByCourseId(courseId);
        if (assignmentInfoList == null) {
            return Result.error("1", "课程没有作业");
        } else {
            return Result.success(assignmentInfoList);
        }
    }

    @PostMapping("/addAssignment")
    public Result addAssignment(String assignmentTitle, String assignmentDescription, Date assignmentDeadline, String assignmentStatus,
                                Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId) {
        boolean flag = assignmentInfoService.addAssignment(assignmentTitle, assignmentDescription, assignmentDeadline, assignmentStatus,
                maxScore, proportion, releaser, releaserType, courseId);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "添加失败");
        }
    }

    @GetMapping("/updateAssignmentByAssignmentId")
    public Result updateAssignment(Integer assignmentId, String assignmentTitle, String assignmentDescription, Date assignmentDeadline, String assignmentStatus,
                                   Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId) {
        boolean flag = assignmentInfoService.updateAssignment(assignmentId, assignmentTitle, assignmentDescription, assignmentDeadline, assignmentStatus,
                maxScore, proportion, releaser, releaserType, courseId);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "更新失败");
        }
    }

    @DeleteMapping("/deleteAssignmentByAssignmentId")
    public Result deleteAssignment(Integer assignmentId) {
        boolean flag = assignmentInfoService.deleteAssignment(assignmentId);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "删除失败");
        }
    }



    //这里返回的是assignment_student表里面的信息，包括了作业的信息和学生的信息
    @GetMapping("/getAssignmentInfoByStudentIdAndCourseId")
    public Result getAssignmentInfoByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        List<AssignmentInfo> assignmentInfoList = assignmentInfoService.findAssignmentInfoByStudentIdAndCourseId(studentId, courseId);
        if (assignmentInfoList == null) {
            return Result.error("1", "该学生没有该课程的作业");
        } else {
            return Result.success(assignmentInfoList);
        }
    }



}

