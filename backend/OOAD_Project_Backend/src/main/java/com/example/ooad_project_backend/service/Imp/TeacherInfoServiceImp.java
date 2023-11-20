package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.mapper.TeacherInfoMapper;
import com.example.ooad_project_backend.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherInfoServiceImp extends ServiceImpl<TeacherInfoMapper, TeacherInfo> implements TeacherInfoService {
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    @Override
    public TeacherInfo findTeacherInfoByAccount(String account) {
        return teacherInfoMapper.findTeacherInfoByAccount(account);
    }
}
