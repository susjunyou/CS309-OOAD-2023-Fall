package com.example.ooad_project_backend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.entity.UserInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.Imp.StudentInfoServiceImp;
import com.example.ooad_project_backend.service.Imp.TeacherInfoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/register")
@CrossOrigin
public class RegisterController {
    @Autowired
    private StudentInfoServiceImp studentInfoService;

    @Autowired
    private TeacherInfoServiceImp teacherInfoServiceImp;

    //注册功能的实现
    @PostMapping("/register")
    public Result register(@RequestBody UserInfo user, HttpServletRequest request) {
        //校验数据有没有填
        if (ObjectUtil.isEmpty(user.getName()) || ObjectUtil.isEmpty(user.getPassword()) || ObjectUtil.isEmpty(user.getUserType())) {
            return Result.error("-1", "Please Input All The Information !");
        }
        if (user.getUserType()== UserType.TEACHER) {
            //教师注册
            TeacherInfo teacherInfo = new TeacherInfo();
            BeanUtil.copyProperties(user, teacherInfo);
            teacherInfoServiceImp.add(teacherInfo);
            //Account拷贝到teacher_info
        }
        if (user.getUserType()== UserType.STUDENT) {
            //学生注册
            StudentInfo studentInfo = new StudentInfo();
            BeanUtil.copyProperties(user,studentInfo);
            studentInfoService.add(studentInfo);

        }

        return Result.success();
    }
}
