package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.mapper.CourseInfoMapper;
import com.example.ooad_project_backend.mapper.CourseTeacherMapper;
import com.example.ooad_project_backend.mapper.TeacherInfoMapper;
import com.example.ooad_project_backend.service.TeacherInfoService;
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
}
