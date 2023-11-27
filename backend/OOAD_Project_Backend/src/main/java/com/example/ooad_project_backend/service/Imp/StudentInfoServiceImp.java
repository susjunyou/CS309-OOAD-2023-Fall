package com.example.ooad_project_backend.service.Imp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.common.ResultCode;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.exception.CustomException;
import com.example.ooad_project_backend.mapper.CourseInfoMapper;
import com.example.ooad_project_backend.mapper.CourseStudentMapper;
import com.example.ooad_project_backend.mapper.StudentInfoMapper;
import com.example.ooad_project_backend.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

    public void add(StudentInfo studentInfo) {
        // 1. 检测数据库中有没有同名的学生，如果有，需要提示前台用户重新输入
        StudentInfo info = studentInfoMapper.findByName(studentInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(studentInfo.getPassword())) {
            studentInfo.setPassword("123456");
        }
        studentInfoMapper.insertStudent(studentInfo);
    }

    @Override
    public boolean submitAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate) {
        studentInfoMapper.submitAssignment(studentId, assignmentId, content, submitDate);
        return true;
    }

    @Override
    public boolean submitProject(Integer studentId, Integer projectId, String content, Date submitDate) {
        studentInfoMapper.submitProject(studentId, projectId, content, submitDate);
        return true;
    }

}
