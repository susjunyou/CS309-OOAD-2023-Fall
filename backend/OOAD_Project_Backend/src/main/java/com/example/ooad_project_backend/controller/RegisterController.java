package com.example.ooad_project_backend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.Imp.StudentInfoServiceImp;
import com.example.ooad_project_backend.service.Imp.TeacherInfoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@CrossOrigin
public class RegisterController {

    @Autowired
    private TeacherInfoServiceImp teacherInfoServiceImp;

    @Autowired
    private StudentInfoServiceImp studentInfoServiceImp;

    //注册功能的实现
    @GetMapping("/registerTeacher")
    public Result register(TeacherInfo user) {
        //校验数据有没有填
        if (ObjectUtil.isEmpty(user.getName()) || ObjectUtil.isEmpty(user.getPassword()) || ObjectUtil.isEmpty(user.getUserType())) {
            return Result.error("-1", "Please Input All The Information !");
        }
        if (user.getUserType() == UserType.TEACHER) {
            //教师注册
            TeacherInfo teacherInfo = new TeacherInfo();
            BeanUtil.copyProperties(user, teacherInfo);
            return teacherInfoServiceImp.add(teacherInfo)? Result.success() : Result.error("-1", "account/ email /phoneNumber already exist");
            //Account拷贝到teacher_info
        }

        return Result.error();
    }

    @GetMapping("/registerStudent")
    public Result registerStu(StudentInfo user) {
        //校验数据有没有填
        if (ObjectUtil.isEmpty(user.getName()) || ObjectUtil.isEmpty(user.getPassword()) || ObjectUtil.isEmpty(user.getUserType())) {
            return Result.error("-1", "Please Input All The Information !");
        }
        if (user.getUserType() == UserType.STUDENT) {
            //学生注册
            StudentInfo studentInfo = new StudentInfo();
            BeanUtil.copyProperties(user, studentInfo);
            return studentInfoServiceImp.add(studentInfo) ? Result.success() : Result.error("-1", "account/ email /phoneNumber already exist");
            //Account拷贝到teacher_info
        }

        return Result.error("-1", "error");
    }
}
