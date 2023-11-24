package com.example.ooad_project_backend.service.Imp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.common.ResultCode;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.exception.CustomException;
import com.example.ooad_project_backend.mapper.CourseInfoMapper;
import com.example.ooad_project_backend.mapper.CourseTeacherMapper;
import com.example.ooad_project_backend.mapper.TeacherInfoMapper;
import com.example.ooad_project_backend.service.TeacherInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherInfoServiceImp extends ServiceImpl<TeacherInfoMapper, TeacherInfo> implements TeacherInfoService {
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public TeacherInfo findTeacherInfoByAccount(String account) {
        return teacherInfoMapper.findTeacherInfoByAccount(account);
    }

    @Override
    public List<CourseInfo> findCourseInfoByTeacherId(Integer teacherId) {
        List<Integer> courseIds = courseTeacherMapper.findCourseIdByTeacherId(teacherId);
        List<CourseInfo> courseInfoList = new ArrayList<>();
        for (Integer courseId : courseIds) {
            courseInfoList.add(courseInfoMapper.findCourseInfoByCourseId(courseId));
        }
        return courseInfoList;
    }



    public void add(TeacherInfo teacherInfo) {
        // 1. 检测数据库中有没有同名的教师，如果有，需要提示前台用户重新输入
        TeacherInfo info = teacherInfoMapper.findByName(teacherInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(teacherInfo.getPassword())) {
            teacherInfo.setPassword("123456");
        }

        teacherInfoMapper.insertTeacherInfo(teacherInfo);
    }


    public List<TeacherInfo> findALl() {
        return teacherInfoMapper.getAll();
    }

    public void update(TeacherInfo teacherInfo) {
        System.out.println(teacherInfo.toString());
        teacherInfoMapper.updateTeacherInfo(teacherInfo);
    }


    public void deleteById(Integer id) {
        teacherInfoMapper.deleteTeacherInfo(id);
    }

    public PageInfo<TeacherInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherInfo> list = teacherInfoMapper.getAll();
        return PageInfo.of(list);
    }


    public PageInfo<TeacherInfo> findPageSearch(String search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherInfo> teacherInfos = teacherInfoMapper.findByLikeName(search);
        return PageInfo.of(teacherInfos);
    }
}
