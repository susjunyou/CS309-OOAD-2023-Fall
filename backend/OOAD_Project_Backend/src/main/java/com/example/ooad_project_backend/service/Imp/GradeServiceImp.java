package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.GradeInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.mapper.GradeMapper;
import com.example.ooad_project_backend.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImp extends ServiceImpl<GradeMapper, GradeInfo> implements GradeService {


    @Autowired
    private GradeMapper gradeMapper;


    @Override
    public List<AssignmentInfo> findAssignmentGrade(Integer studentId, Integer AssignmentId) {
        return gradeMapper.findAssignmentGrade(studentId, AssignmentId);
    }

    @Override
    public List<ProjectInfo> findProjectGrade(Integer studentId, Integer projectId) {
        return gradeMapper.findProjectGrade(studentId, projectId);
    }
}