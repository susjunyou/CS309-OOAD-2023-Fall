package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.AdminInfo;

public interface AdminInfoService extends IService<AdminInfo> {

    AdminInfo AdminLogin(String account);
}
