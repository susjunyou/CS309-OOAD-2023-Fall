package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentInfoService studentInfoService;


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
    public Result updateAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate) {
        return studentInfoService.updateAssignment(studentId, assignmentId, content, submitDate) ? Result.success() : Result.error("1", "提交失败");
    }

    @GetMapping("/submitAssignment")
    public Result submitAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate) {
        // 自动填充提交时间为当前时间，暂未实现
        return studentInfoService.submitAssignment(studentId, assignmentId, content, submitDate) ? Result.success() : Result.error("1", "提交失败");
    }

    @GetMapping("/submitProject")
    public Result submitProject(Integer studentId, Integer projectId, String content, Date submitDate) {
        return studentInfoService.submitProject(studentId, projectId, content, submitDate) ? Result.success() : Result.error("1", "提交失败");
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
    public Result updateStudentDetails(Integer id, String email, String phoneNumber, String selfIntroduction) {
        return studentInfoService.updateStudentDetails(id, email, phoneNumber, selfIntroduction) ? Result.success() : Result.error("1", "修改失败");
    }


}
