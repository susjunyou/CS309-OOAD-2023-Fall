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

//    @GetMapping("/getAttendanceGradeByStudentId")
//    public Result getAttendanceGradeByStudentId(Integer studentId) {
//        List<AttendanceInfo> attendanceInfos = gradeServiceImp.findAttendanceGradeByStudentId(studentId);
//        if (attendanceInfos.size() == 0) {
//            return Result.error("1", "No such student or the student does not check in !");
//        } else {
//            return Result.success(attendanceInfos);
//        }
//    }

    @GetMapping("/getAttendanceGradeByCourseIdAndStudentId")
    public Result getAttendanceGradeByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        List<AttendanceInfo> attendanceInfos = gradeServiceImp.findAttendanceGradeByCourseIdAndStudentId(courseId, studentId);
        if (attendanceInfos.size() == 0) {
            return Result.error("1", "No such student or the student does not check in !");
        } else {
            return Result.success(attendanceInfos);
        }
    }

    //除了三个必须的参数，其他的content和grade_description都可以不填
    @PostMapping("/addAssignmentGrade")
    public Result addAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade, String content, String grade_description) {
        return gradeServiceImp.addAssignmentGrade(studentId, assignmentId, grade,content,grade_description) ? Result.success() : Result.error("1", "提交失败");
    }

    @PutMapping("/updateAssignmentGrade")
    public Result updateAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade, String content, String grade_description) {
        return gradeServiceImp.updateAssignmentGrade(studentId, assignmentId, grade,content,grade_description) ? Result.success() : Result.error("1", "提交失败");
    }





    @GetMapping("/getProjectGrade")
    public Result getProjectGrade(Integer studentId, Integer projectId) {
        List<ProjectInfo> projectInfos = gradeServiceImp.findProjectGrade(studentId, projectId);
        if (projectInfos.size() == 0) {
            return Result.error("1", "No such project");
        } else {
            return Result.success(projectInfos);
        }
    }

    @PostMapping("/addProjectGrade")
    public Result addProjectGrade(Integer studentId, Integer projectId, Integer grade, String content, String grade_description) {
        return gradeServiceImp.addProjectGrade(studentId, projectId, grade,content,grade_description) ? Result.success() : Result.error("1", "提交失败");
    }

    @PutMapping("/updateProjectGrade")
    public Result updateProjectGrade(Integer studentId, Integer projectSubId, Integer grade, String content, String grade_description) {
        //注意这里的projet_id是project_submission_id!!
        return gradeServiceImp.updateProjectGrade(studentId, projectSubId, grade,content,grade_description) ? Result.success() : Result.error("1", "提交失败");
    }
}


