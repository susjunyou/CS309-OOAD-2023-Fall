package com.example.ooad_project_backend.service.Imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.AdminInfo;
import com.example.ooad_project_backend.mapper.AdminInfoMapper;
import com.example.ooad_project_backend.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminInfoServiceImp extends ServiceImpl<AdminInfoMapper, AdminInfo> implements AdminInfoService {
    @Autowired
    private AdminInfoMapper adminInfoMapper;


    @Override
    public AdminInfo findAdminInfoByAccount(String account) {
        //通过用户名和密码去数据库查一条记录
        return adminInfoMapper.findAdminInfoByName(account);
    }

    @Override
    public AdminInfo findAdminByEmail(String email) {
        return adminInfoMapper.findAdminInfoByEmail(email);
    }

    @Override
    public AdminInfo findAdminByPhoneNumber(String phoneNumber) {
        return adminInfoMapper.findAdminInfoByPhoneNumber(phoneNumber);
    }
}

