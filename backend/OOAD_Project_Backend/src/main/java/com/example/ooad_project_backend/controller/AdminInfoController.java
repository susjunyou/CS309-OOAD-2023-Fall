package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.service.Imp.AdminInfoServiceImp;
import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.AdminInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/adminInfo")
@CrossOrigin
public class AdminInfoController {
    @Autowired
    private AdminInfoServiceImp adminInfoService;

    @GetMapping("/login")
    public Result AdminLogin(String name, String password) {
        AdminInfo adminInfo = adminInfoService.AdminLogin(name, password);
        if (adminInfo!= null) {
            return Result.success(adminInfo);
        } else {
            return Result.error();
        }
    }
}
