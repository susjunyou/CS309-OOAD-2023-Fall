package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TeacherInfoService extends IService<TeacherInfo> {
    TeacherInfo findTeacherInfoByAccount(String account);

    TeacherInfo findTeacherInfoByEmail(String email);

    TeacherInfo findTeacherInfoByPhoneNumber(String phoneNumber);

    List<CourseInfo> findCourseInfoByTeacherId(Integer teacherId);

     List<TeacherInfo> findALl();

     void update(TeacherInfo teacherInfo);

     void deleteById(Integer id);

     PageInfo<TeacherInfo> findPage(Integer pageNum, Integer pageSize);

     PageInfo<TeacherInfo> findPageSearch(String search, Integer pageNum, Integer pageSize);

     boolean add(TeacherInfo teacherInfo);
}
