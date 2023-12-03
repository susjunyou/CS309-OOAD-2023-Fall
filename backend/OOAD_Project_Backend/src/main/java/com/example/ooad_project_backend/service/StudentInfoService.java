package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.CourseInfo;
import com.example.ooad_project_backend.entity.FileInfo;
import com.example.ooad_project_backend.entity.StudentInfo;

import java.sql.Date;
import java.util.List;

public interface StudentInfoService extends IService<StudentInfo> {
    StudentInfo findStudentInfoByAccount(String account);

    List<CourseInfo> findCourseInfoByStudentId(Integer studentId);

    void add(StudentInfo studentInfo);

    boolean submitAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate, Integer fileId);

    boolean submitProject(Integer studentId, Integer projectId, String content, Date submitDate, Integer fileId);

    StudentInfo findStudentInfoById(Integer id);

    boolean updateAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate);

    boolean updatePassword(Integer id, String password);

    boolean updateStudentDetails(Integer id, String email, String phoneNumber, String selfIntroduction);
}
