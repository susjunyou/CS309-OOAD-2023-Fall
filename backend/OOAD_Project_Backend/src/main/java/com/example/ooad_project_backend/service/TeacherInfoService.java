package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.TeacherInfo;

public interface TeacherInfoService extends IService<TeacherInfo> {
    TeacherInfo findTeacherInfoByAccount(String account);
}
