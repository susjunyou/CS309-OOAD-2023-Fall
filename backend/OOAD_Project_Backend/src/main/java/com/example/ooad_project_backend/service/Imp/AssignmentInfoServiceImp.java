package com.example.ooad_project_backend.service.Imp;

import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.mapper.AssignmentMapper;
import com.example.ooad_project_backend.service.AssignmentInfoService;

import java.util.List;

public class AssignmentInfoServiceImp implements AssignmentInfoService {

    private AssignmentMapper assignmentMapper;
    @Override
    public List<AssignmentInfo> findAssignmentInfoByCourseId(Integer courseId) {
        return assignmentMapper.findAllAssignmentInfoByCourseId(courseId);
    }
}
