package com.example.ooad_project_backend.Controller;

import com.example.ooad_project_backend.Service.AdminInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/adminInfo")
public class AdminInfoController {
    @Resource
    private AdminInfoService adminInfoService;
}
