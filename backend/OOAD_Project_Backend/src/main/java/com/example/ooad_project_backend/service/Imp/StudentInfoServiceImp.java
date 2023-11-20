package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.mapper.StudentInfoMapper;
import com.example.ooad_project_backend.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentInfoServiceImp extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Override
    public StudentInfo findStudentInfoByAccount(String account) {
        return studentInfoMapper.findStudentInfoByName(account);
    }
}
