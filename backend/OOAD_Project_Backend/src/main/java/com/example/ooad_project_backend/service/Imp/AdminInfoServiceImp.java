package com.example.ooad_project_backend.service.Imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.mapper.AdminInfoMapper;
import com.example.ooad_project_backend.service.AdminInfoService;
import com.example.ooad_project_backend.entity.AdminInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AdminInfoServiceImp extends ServiceImpl<AdminInfoMapper, AdminInfo> implements AdminInfoService {
    @Autowired
    private AdminInfoMapper adminInfoMapper;


    @Override
    public AdminInfo AdminLogin(String name, String password) {
        //通过用户名和密码去数据库查一条记录
        AdminInfo adminInfo = adminInfoMapper.findByNameAndPass(name, password);
        return adminInfo;
    }
}

