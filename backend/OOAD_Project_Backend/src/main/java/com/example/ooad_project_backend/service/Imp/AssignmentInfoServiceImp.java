package com.example.ooad_project_backend.service.Imp;

import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.enums.StatusType;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.mapper.AssignmentMapper;
import com.example.ooad_project_backend.service.AssignmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class AssignmentInfoServiceImp implements AssignmentInfoService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    public List<AssignmentInfo> findAssignmentInfoByCourseId(Integer courseId) {
        return assignmentMapper.findAllAssignmentInfoByCourseId(courseId);
    }

    @Override
    public List<AssignmentInfo> findAssignmentInfoByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return assignmentMapper.findAssignmentInfoByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public boolean updateAssignment(Integer assignmentId, String assignmentTitle, String assignmentDescription, Date assignmentDeadline, StatusType assignmentStatus,
                                    Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId) {
        return assignmentMapper.updateAssignment(assignmentId, assignmentTitle, assignmentDescription, assignmentDeadline, assignmentStatus,
                maxScore, proportion, releaser, releaserType, courseId);
    }


    @Override
    public boolean addAssignment(String assignmentTitle, String assignmentDescription, Date assignmentDeadline, StatusType assignmentStatus,
                                 Integer maxScore, Double proportion, Integer  releaser, UserType releaserType, Integer courseId) {
        return assignmentMapper.addAssignment(assignmentTitle, assignmentDescription, assignmentDeadline, assignmentStatus,
                maxScore, proportion, releaser, releaserType, courseId);
    }

    @Override
    public boolean deleteAssignment(Integer assignmentId) {
        return assignmentMapper.deleteById(assignmentId);
    }
}
