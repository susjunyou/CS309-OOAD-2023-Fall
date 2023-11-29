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
public class SAController {
    @Autowired
    private StudentInfoService studentInfoService;



}

