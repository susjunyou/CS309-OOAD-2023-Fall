package com.example.ooad_project_backend.service.Imp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.mapper.CourseInfoMapper;
import com.example.ooad_project_backend.mapper.CourseStudentMapper;
import com.example.ooad_project_backend.mapper.StudentInfoMapper;
import com.example.ooad_project_backend.mapper.TeamMapper;
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


    @Autowired
    private TeamMapper teamMapper;

    @Override
    public StudentInfo findStudentInfoByAccount(String account) {
        return studentInfoMapper.findStudentInfoByName(account);
    }

    @Override
    public StudentInfo findStudentInfoByEmail(String email) {
        return studentInfoMapper.findStudentInfoByEmail(email);
    }

    @Override
    public StudentInfo findStudentInfoByPhoneNumber(String phoneNumber) {
        return studentInfoMapper.findStudentInfoByPhoneNumber(phoneNumber);
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

    public boolean add(StudentInfo studentInfo) {
        // 1. 检测数据库中有没有同名的学生，如果有，需要提示前台用户重新输入
        StudentInfo info = studentInfoMapper.findByName(studentInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            return false;
        }
        info = studentInfoMapper.findStudentInfoByEmail(studentInfo.getEmail());
        if (ObjectUtil.isNotEmpty(info)) {
            return false;
        }
        info = studentInfoMapper.findStudentInfoByPhoneNumber(studentInfo.getPhoneNumber());
        if (ObjectUtil.isNotEmpty(info)) {
            return false;
        }
        info = studentInfoMapper.findStudentInfoByName(studentInfo.getAccount());
        if (ObjectUtil.isNotEmpty(info)) {
            return false;
        }
        if (ObjectUtil.isEmpty(studentInfo.getPassword())) {
            studentInfo.setPassword("123456");
        }
        studentInfoMapper.insertStudent(studentInfo);
        return true;
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
    public boolean submitProject(Integer teamId, Integer projectId, String content, Date submitDate, Integer fileId) {
        List<Integer> studentIds = teamMapper.findStudentIdsByTeamId(teamId);
        for (Integer studentId : studentIds) {
            studentInfoMapper.submitProject(studentId, projectId, content, submitDate, fileId);
        }
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
