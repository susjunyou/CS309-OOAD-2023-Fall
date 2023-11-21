package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.entity.AdminInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.Imp.AdminInfoServiceImp;
import com.example.ooad_project_backend.service.Imp.StudentInfoServiceImp;
import com.example.ooad_project_backend.service.Imp.TeacherInfoServiceImp;
import com.example.ooad_project_backend.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private AdminInfoServiceImp adminInfoService;

    @Autowired
    private StudentInfoServiceImp studentInfoService;

    @Autowired
    private TeacherInfoServiceImp teacherInfoService;


    @GetMapping("/admin")
    public Result adminLogin(String account, String password) {
        AdminInfo adminInfo = adminInfoService.findAdminInfoByAccount(account);
        if (adminInfo != null && adminInfo.getPassword() != null && adminInfo.getPassword().equals(password)) {
            adminInfo.setUserType(UserType.ADMIN);
            return Result.success(adminInfo);
        } else {
            return Result.error("1", "账号或密码错误");
        }
    }

    @GetMapping("/student")
    public Result studentLogin(String account, String password) {
        StudentInfo studentInfo = studentInfoService.findStudentInfoByAccount(account);
        if (studentInfo != null && studentInfo.getPassword() != null && studentInfo.getPassword().equals(password)) {
            studentInfo.setUserType(UserType.STUDENT);
            return Result.success(studentInfo);
        } else {
            return Result.error("1", "账号或密码错误");
        }
    }

    @GetMapping("/teacher")
    public Result teacherLogin(String account, String password) {
        TeacherInfo teacherInfo = teacherInfoService.findTeacherInfoByAccount(account);
        if (teacherInfo != null && teacherInfo.getPassword() != null && teacherInfo.getPassword().equals(password)) {
            teacherInfo.setUserType(UserType.TEACHER);
            return Result.success(teacherInfo);
        } else {
            return Result.error("1", "账号或密码错误");
        }
    }


}
