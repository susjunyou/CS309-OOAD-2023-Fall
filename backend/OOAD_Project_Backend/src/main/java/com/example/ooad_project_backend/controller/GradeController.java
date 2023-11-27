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
@RequestMapping("/grade")
@CrossOrigin
public class GradeController {

    @Autowired
    private GradeServiceImp gradeServiceImp;

    @GetMapping("/AssignmentGrade")
    public Result getAssignmentGrade(Integer studentId, Integer assignmentId) {
        List<AssignmentInfo> assignmentInfos = gradeServiceImp.findAssignmentGrade(studentId, assignmentId);
        if (assignmentInfos.size() == 0) {
            return Result.error("1", "No such assignment");
        } else {
            return Result.success(assignmentInfos);
        }
    }

    @GetMapping("/ProjectGrade")
    public Result getProjectGrade(Integer studentId, Integer projectId) {
        List<ProjectInfo> projectInfos = gradeServiceImp.findProjectGrade(studentId, projectId);
        if (projectInfos.size() == 0) {
            return Result.error("1", "No such project");
        } else {
            return Result.success(projectInfos);
        }
    }
}
