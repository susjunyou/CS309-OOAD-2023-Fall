package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.service.Imp.StudentInfoServiceImp;
import com.example.ooad_project_backend.service.Imp.TeacherInfoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

public class RegisterController {
    @Resource
    private StudentInfoServiceImp studentInfoService;

}
