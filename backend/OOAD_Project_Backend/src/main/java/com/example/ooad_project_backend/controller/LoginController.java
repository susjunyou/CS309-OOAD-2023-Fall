package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.entity.AdminInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.Imp.AdminInfoServiceImp;
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

    @GetMapping("/Admin")
    public Result AdminLogin(String account, String password) {
        AdminInfo adminInfo = adminInfoService.AdminLogin(account);
        if (adminInfo != null && adminInfo.getPassword() != null && adminInfo.getPassword().equals(password)) {
            adminInfo.setUserType(UserType.ADMIN);
            return Result.success(adminInfo);
        } else {
            return Result.error("1", "账号或密码错误");
        }
    }
}
