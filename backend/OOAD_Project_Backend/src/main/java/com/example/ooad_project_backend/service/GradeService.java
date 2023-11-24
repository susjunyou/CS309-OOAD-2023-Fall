package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.GradeInfo;

import java.util.List;

public interface GradeService extends IService<GradeInfo> {

    List<GradeInfo> findAssignmentGrade();

    List<GradeInfo> findProjectGrade();
}
