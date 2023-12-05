package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.service.Imp.GradeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/submission")
@CrossOrigin
public class SubmissionController{

    @Autowired
    private GradeServiceImp gradeServiceImp;


    //返回某个学生某个作业的提交历史，包括提交时间和提交人id，描述，分数，后续可依据需求添加
    @GetMapping("/getAssignmentSubmissionHistory")
    public Result getAssignmentSubmissionHistory(Integer studentId, Integer assignmentId) {
        List<AssignmentInfo> assignmentInfos = gradeServiceImp.getAssignmentSubmissionHistory(studentId, assignmentId);
        if (assignmentInfos.size() == 0) {
            return Result.error("1", "No such assignment");
        } else {
            return Result.success(assignmentInfos);
        }
    }

    //返回成绩，提交历史，提交人id，描述，分数，后续可依据需求添加
    @GetMapping("/getProjectSubmissionHistory")
    public Result getProjectSubmissionHistory(Integer studentId, Integer projectId) {
        List<ProjectInfo> projectInfos = gradeServiceImp.getProjectSubmissionHistory(studentId, projectId);
        if (projectInfos.size() == 0) {
            return Result.error("1", "No such project");
        } else {
            return Result.success(projectInfos);
        }
    }




}
