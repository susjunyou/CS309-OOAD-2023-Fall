package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.StudentInfo;

import java.util.List;

public interface StudentInfoService extends IService<StudentInfo> {
    StudentInfo findStudentInfoByAccount(String account);

    List<CourseInfo> findCourseInfoByStudentId(Integer studentId);
    public void add(StudentInfo studentInfo);

}
