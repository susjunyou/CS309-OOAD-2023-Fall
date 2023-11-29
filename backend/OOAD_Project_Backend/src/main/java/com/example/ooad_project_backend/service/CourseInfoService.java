package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.*;

import java.util.List;


public interface CourseInfoService extends IService<CourseInfo> {
    List<ProjectInfo> findProjectInfoByCourseId(Integer courseId);

    List<AssignmentInfo> findAssignmentInfoByCourseId(Integer courseId);

    List<PostInfo> findPostInfoByCourseId(Integer courseId);

    List<AttendanceInfo> findAttendanceInfoByCourseId(Integer courseId);

    List<MaterialInfo> findMaterialInfoByCourseId(Integer courseId);

    public void addStudent(Integer courseId, Integer studentId);

    public void addSA(Integer courseId, Integer studentId);

    public void addTeacher(Integer courseId, Integer teacherId);
}
