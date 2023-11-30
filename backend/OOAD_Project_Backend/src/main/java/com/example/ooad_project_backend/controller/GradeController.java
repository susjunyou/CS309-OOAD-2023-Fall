package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.AttendanceInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.service.Imp.GradeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAttendanceGradeByStudentId")
    public Result getAttendanceGradeByStudentId(Integer studentId) {
        List<AttendanceInfo> attendanceInfos = gradeServiceImp.findAttendanceGradeByStudentId(studentId);
        if (attendanceInfos.size() == 0) {
            return Result.error("1", "No such student or the student does not check in !");
        } else {
            return Result.success(attendanceInfos);
        }
    }


    @PostMapping("/addAssignmentGrade")
    public Result addAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade) {
        return gradeServiceImp.addAssignmentGrade(studentId, assignmentId, grade) ? Result.success() : Result.error("1", "提交失败");
    }

    @GetMapping("/updateAssignmentGrade")
    public Result updateAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade) {
        return gradeServiceImp.addAssignmentGrade(studentId, assignmentId, grade) ? Result.success() : Result.error("1", "提交失败");
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
