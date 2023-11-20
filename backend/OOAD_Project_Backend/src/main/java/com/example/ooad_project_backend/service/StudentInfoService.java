package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.StudentInfo;

public interface StudentInfoService extends IService<StudentInfo> {
    StudentInfo findStudentInfoByAccount(String account);
}
