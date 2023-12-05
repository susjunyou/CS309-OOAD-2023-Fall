package com.example.ooad_project_backend.service.Imp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.common.ResultCode;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
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
    public boolean submitAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate, Integer fileId) {
        // 自动填充提交时间为当前时间
        studentInfoMapper.submitAssignment(studentId, assignmentId, content, submitDate, fileId);
        return true;
    }

    @Override
    public boolean updateAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate, String technologyStack, String programmingSkill, String intendedTeammate) {
        studentInfoMapper.updateAssignment(studentId, assignmentId, content, submitDate, technologyStack, programmingSkill, intendedTeammate);
        return true;
    }

    @Override
    public boolean submitProject(Integer studentId, Integer projectId, String content, Date submitDate, Integer fileId) {
        studentInfoMapper.submitProject(studentId, projectId, content, submitDate, fileId);
        return true;
    }

    @Override
    public StudentInfo findStudentInfoById(Integer id) {
        return studentInfoMapper.findStudentInfoById(id);
    }

    @Override
    public boolean updatePassword(Integer id, String password) {
        studentInfoMapper.updatePassword(id, password);
        return true;
    }

    @Override
    public boolean updateStudentDetails(Integer id, String email, String phoneNumber, String selfIntroduction, String technologyStack, String programmingSkill, String intendedTeammate) {
        studentInfoMapper.updateStudentDetails(id, email, phoneNumber, selfIntroduction, technologyStack, programmingSkill, intendedTeammate);
        return true;
    }

    @Override
    public boolean addSA(Integer studentId, Integer courseId) {
        studentInfoMapper.addSA(studentId, courseId);
        studentInfoMapper.trueSA(studentId, courseId);
        return true;
    }

    @Override
    public boolean deleteSA(Integer studentId, Integer courseId) {
        studentInfoMapper.deleteSA(studentId, courseId);
        if (studentInfoMapper.findStudentIsSA(studentId).isEmpty())
            studentInfoMapper.falseSA(studentId, courseId);
        return true;
    }

    @Override
    public String findEmailByStudentId(Integer studentId) {
        return studentInfoMapper.findEmailByStudentId(studentId);
    }

}
