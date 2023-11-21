package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.mapper.CourseInfoMapper;
import com.example.ooad_project_backend.mapper.CourseStudentMapper;
import com.example.ooad_project_backend.mapper.StudentInfoMapper;
import com.example.ooad_project_backend.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentInfoServiceImp extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Override
    public StudentInfo findStudentInfoByAccount(String account) {
        return studentInfoMapper.findStudentInfoByName(account);
    }

    @Override
    public List<CourseInfo> findCourseInfoByStudentId(Integer studentId) {
        List<Integer> courseIds = courseStudentMapper.findCourseIdByStudentId(studentId);
        List<CourseInfo> courseInfoList = new ArrayList<>();
        for (Integer courseId : courseIds) {
            courseInfoList.add(courseInfoMapper.findCourseInfoByCourseId(courseId));
        }
        return courseInfoList;
    }


}
