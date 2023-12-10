package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.FileInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.Imp.FileServiceImp;
import com.example.ooad_project_backend.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentInfoService studentInfoService;

    @Autowired
    private FileServiceImp fileServiceImp;


    @GetMapping("/getCourseInfo")
    public Result getCoursesByStudentId(Integer studentId) {
        List<CourseInfo> courseInfoList = studentInfoService.findCourseInfoByStudentId(studentId);
        if (courseInfoList != null) {
            return Result.success(courseInfoList);
        } else {
            return Result.error("1", "该学生没有课程");
        }
    }


    @GetMapping("/updateAssignment")
    public Result updateAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate, String technologyStack, String programmingSkill, String intendedTeammate) {
        return studentInfoService.updateAssignment(studentId, assignmentId, content, submitDate, technologyStack, programmingSkill, intendedTeammate) ? Result.success() : Result.error("1", "提交失败");
    }

    @PostMapping("/submitAssignment")
    public Result submitAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate, MultipartFile file) {
        // 自动填充提交时间为当前时间，暂未实现
        FileInfo fileInfo = new FileInfo();
        try {
            if (file != null) {
                fileInfo.setFileName(file.getOriginalFilename());
                fileInfo.setFileType(file.getContentType());
                fileInfo.setFileData(file.getBytes());
                fileInfo = fileServiceImp.insertFile(fileInfo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return studentInfoService.submitAssignment(studentId, assignmentId, content, submitDate, fileInfo.getId()) ? Result.success() : Result.error("1", "提交失败");
    }

    @PostMapping("/submitProject")
    public Result submitProject(Integer teamId, Integer projectId, String content, Date submitDate, MultipartFile file) {
        FileInfo fileInfo = new FileInfo();
        try {
            if (file != null) {
                fileInfo.setFileName(file.getOriginalFilename());
                fileInfo.setFileType(file.getContentType());
                fileInfo.setFileData(file.getBytes());
                fileInfo.setId(fileServiceImp.insertFile(fileInfo).getId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return studentInfoService.submitProject(teamId, projectId, content, submitDate, fileInfo.getId()) ? Result.success() : Result.error("1", "提交失败");
    }

    @GetMapping("/getStudent")
    public Result getStudentInfoById(Integer id) {
        StudentInfo studentInfo = studentInfoService.findStudentInfoById(id);
        if (studentInfo != null) {
            studentInfo.setUserType(UserType.STUDENT);
            return Result.success(studentInfo);
        } else {
            return Result.error("1", "该学生不存在");
        }
    }

    @GetMapping("/updatePassword")
    public Result updatePassword(Integer id, String password) {
        return studentInfoService.updatePassword(id, password) ? Result.success() : Result.error("1", "修改失败");
    }

    @GetMapping("/updateStudentDetails")
    public Result updateStudentDetails(Integer id, String email, String phoneNumber, String selfIntroduction, String technologyStack, String programmingSkill, String intendedTeammate) {
        return studentInfoService.updateStudentDetails(id, email, phoneNumber, selfIntroduction, technologyStack, programmingSkill, intendedTeammate) ? Result.success() : Result.error("1", "修改失败");
    }


}
