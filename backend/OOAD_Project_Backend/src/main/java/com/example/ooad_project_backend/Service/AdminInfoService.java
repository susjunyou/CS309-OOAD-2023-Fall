package com.example.ooad_project_backend.Service;

import cn.hutool.core.util.ObjectUtil;
import com.example.ooad_project_backend.Dao.AdminInfoDao;
import com.example.ooad_project_backend.entity.UserInfo;
import com.example.ooad_project_backend.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminInfoService {
    @Resource
    private AdminInfoDao adminInfoDao;


    public UserInfo login(String name, String password) {
        //通过用户名和密码去数据库查一条记录
        UserInfo adminInfo = adminInfoDao.findByNameAndPass(name, password);

        if (ObjectUtil.isEmpty((adminInfo))){
            throw new CustomException("-1","Wrong User、Password or Role!");
        }

        return adminInfo;
    }
}

