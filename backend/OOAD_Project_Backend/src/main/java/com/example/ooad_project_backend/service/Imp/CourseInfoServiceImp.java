package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.*;
import com.example.ooad_project_backend.mapper.CourseDetailsMapper;
import com.example.ooad_project_backend.mapper.CourseInfoMapper;
import com.example.ooad_project_backend.mapper.ProjectInfoMapper;
import com.example.ooad_project_backend.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseInfoServiceImp extends ServiceImpl<CourseInfoMapper, CourseInfo> implements CourseInfoService {

    @Autowired
    private CourseDetailsMapper courseDetailsMapper;

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Override
    public List<ProjectInfo> findProjectInfoByCourseId(Integer courseId) {
        return projectInfoMapper.findProjectInfoByCourseId(courseId);
    }

    @Override
    public List<AssignmentInfo> findAssignmentInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findAssignmentInfoByCourseId(courseId);
    }

    @Override
    public List<PostInfo> findPostInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findPostInfoByCourseId(courseId);
    }

    @Override
    public List<AttendanceInfo> findAttendanceInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findAttendanceInfoByCourseId(courseId);
    }

    @Override
    public List<MaterialInfo> findMaterialInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findMaterialInfoByCourseId(courseId);
    }

    @Override
    public void addStudent(Integer courseId, Integer studentId) {
        courseDetailsMapper.insertStudent(courseId, studentId);
    }

    @Override
    public void addSA(Integer courseId, Integer studentId) {
        courseDetailsMapper.insertSA(courseId, studentId);
    }

    @Override
    public void addTeacher(Integer courseId, Integer teacherId) {
        courseDetailsMapper.insertTeacher(courseId, teacherId);
    }

    @Override
    public List<Integer> findMySACourseIdByStudentId(Integer studentId) {
        return courseDetailsMapper.findMySACourseIdByStudentId(studentId);
    }

    @Override
    public List<Integer> findMySAIdByCourseId(Integer courseId) {
        return courseDetailsMapper.findMySAIdByCourseId(courseId);
    }

    @Override
    public List<StudentInfo> findAllStudentInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findAllStudentInfoByCourseId(courseId);
    }

    @Override
    public List<StudentInfo> findAllSAInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findAllSAInfoByCourseId(courseId);
    }
}
