package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TeacherInfoService extends IService<TeacherInfo> {
    TeacherInfo findTeacherInfoByAccount(String account);

    List<CourseInfo> findCourseInfoByTeacherId(Integer teacherId);
    public List<TeacherInfo> findALl();
    public void update(TeacherInfo teacherInfo);
    public void deleteById(Integer id);
    public PageInfo<TeacherInfo> findPage(Integer pageNum, Integer pageSize);
    public PageInfo<TeacherInfo> findPageSearch(String search, Integer pageNum, Integer pageSize);
    public void add(TeacherInfo teacherInfo);
}
