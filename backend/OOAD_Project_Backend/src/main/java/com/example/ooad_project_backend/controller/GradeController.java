package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.AttendanceInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/grade")
@CrossOrigin
public class GradeController {

    @Autowired
    private GradeService gradeService;

    //findAssignmentGrade在impl里面已经实现了判断true的功能，所以这里不用再判断了
    @GetMapping("/getAssignmentGrade")
    public Result getAssignmentGrade(Integer studentId, Integer assignmentId) {
        AssignmentInfo assignmentInfo = gradeService.findAssignmentGrade(studentId, assignmentId);
        return assignmentInfo == null ? Result.error("1", "No such assignment or the student has not submitted the assignment!") : Result.success(assignmentInfo);
    }


    @GetMapping("/getAttendanceGradeByCourseIdAndStudentId")
    public Result getAttendanceGradeByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        List<AttendanceInfo> attendanceInfos = gradeService.findAttendanceGradeByCourseIdAndStudentId(courseId, studentId);
        if (attendanceInfos.size() == 0) {
            return Result.error("1", "No such student or the student does not check in !");
        } else {
            return Result.success(attendanceInfos);
        }
    }

    @GetMapping("/getAttendanceGrade")
    public Result getAttendanceGrade(Integer studentId, Integer attendanceId) {
        return gradeService.findAttendanceGrade(studentId, attendanceId) == null ? Result.error("1", "No such attendance or the student has not submitted the attendance!") : Result.success(gradeService.findAttendanceGrade(studentId, attendanceId));
    }

    //除了三个必须的参数，其他的content和grade_description都可以不填
    @GetMapping("/addAssignmentGrade")
    public Result addAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade, String content, String grade_description) {
        return gradeService.addAssignmentGrade(studentId, assignmentId, grade, content, grade_description) ? Result.success() : Result.error("1", "提交失败");
    }

    @GetMapping("/updateAssignmentGrade")
    public Result updateAssignmentGrade(Integer studentId, Integer grade, String grade_description, Integer assignmentSubmissionId) {
//        List<AssignmentInfo> assignmentInfos = gradeService.findAssignmentGrade(studentId, assignmentId);
//        if (assignmentInfos.size() == 0) {
//            return Result.error("1", "No such assignment");
//        }
//        for (AssignmentInfo assignmentInfo : assignmentInfos) {
//            if (assignmentInfo.isFresh()) {
//                //在数据库里把is_fresh设为false
//                gradeService.freshAss(studentId, assignmentId);
//            }
//        }
        return gradeService.updateAssignmentGrade(studentId, assignmentSubmissionId, grade, grade_description) ? Result.success() : Result.error("1", "提交失败");
    }


    //findProjectGrade在impl里面已经实现了判断true的功能，所以这里不用再判断了
    @GetMapping("/getProjectGrade")
    public Result getProjectGrade(Integer studentId, Integer projectId) {
        ProjectInfo projectInfo = gradeService.findProjectGrade(studentId, projectId);
        return projectInfo != null ? Result.success(projectInfo) : Result.error("1", "No such project");
    }

    @GetMapping("/addProjectGrade")
    public Result addProjectGrade(Integer studentId, Integer projectId, Integer grade, String content, String grade_description) {
        return gradeService.addProjectGrade(studentId, projectId, grade, content, grade_description) ? Result.success() : Result.error("1", "提交失败");
    }

    @GetMapping("/updateProjectGrade")
    public Result updateProjectGrade(Integer studentId, Integer grade, String grade_description, Integer projectSubmissionId) {
//        List<ProjectInfo> projectInfos = gradeService.findProjectGrade(studentId, projectSubId);
//        if (projectInfos.size() == 0) {
//            return Result.error("1", "No such project");
//        }
//        for (ProjectInfo projectInfo : projectInfos) {
//            if (projectInfo.isFresh()) {
//                //在数据库里把is_fresh设为false
//                gradeService.freshPro(studentId, projectSubId);
//            }
//        }
        return gradeService.updateProjectGrade(studentId, projectSubmissionId, grade, grade_description) ? Result.success() : Result.error("1", "提交失败");
    }


    @PostMapping("/uploadAssignmentGradeCSV")
    public Result uploadAssignmentGradeCSV(Integer assignmentId, MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String studentId = fields[0];
                String grade = fields[1];
                if (!gradeService.uploadAssignmentGradeCSV(assignmentId, Integer.parseInt(studentId), Integer.parseInt(grade)))
                    return Result.error();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }

    @PostMapping("/uploadProjectGradeCSV")
    public Result uploadProjectGradeCSV(Integer projectId, MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String studentId = fields[0];
                String grade = fields[1];
                if (!gradeService.uploadProjectGradeCSV( projectId, Integer.parseInt(studentId),Integer.parseInt(grade)))
                    return Result.error();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }

    @PostMapping("/uploadAttendanceGradeCSV")
    public Result uploadAttendanceGradeCSV(Integer attendanceId, MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String studentId = fields[0];
                String grade = fields[1];
                if (!gradeService.uploadAttendanceGradeCSV(attendanceId, Integer.parseInt(studentId), Integer.parseInt(grade)))
                    return Result.error();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }
}


