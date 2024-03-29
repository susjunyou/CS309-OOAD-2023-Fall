package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.StudentInfo;

import java.sql.Date;
import java.util.List;

public interface StudentInfoService extends IService<StudentInfo> {
    StudentInfo findStudentInfoByAccount(String account);

    StudentInfo findStudentInfoByEmail(String email);

    StudentInfo findStudentInfoByPhoneNumber(String phoneNumber);

    List<CourseInfo> findCourseInfoByStudentId(Integer studentId);

    boolean add(StudentInfo studentInfo);

    boolean submitAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate, Integer fileId);

    boolean submitProject(Integer studentId, Integer projectId, String content, Date submitDate, Integer fileId);

    StudentInfo findStudentInfoById(Integer id);

    boolean updateAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate, String technologyStack, String programmingSkill, String intendedTeammate);

    boolean updatePassword(Integer id, String password);

    boolean updateStudentDetails(Integer id, String email, String phoneNumber, String selfIntroduction, String technologyStack, String programmingSkill, String intendedTeammate);

    boolean addSA(Integer studentId, Integer courseId);

    boolean deleteSA(Integer studentId, Integer courseId);

    String findEmailByStudentId(Integer studentId);
}
