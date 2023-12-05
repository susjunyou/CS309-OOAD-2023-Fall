package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.service.AssignmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@SuppressWarnings("ALL")
@RestController
@RequestMapping("/assignment")
@CrossOrigin
public class AssignmentController {
//SA老师查看某门课的所有作业，推荐之后的方法都在这里写，不然好难找T.T
    @Autowired
    private AssignmentInfoService assignmentInfoService;

    //提交作业部分在StudentController里面

    @GetMapping("/getAssignmentInfoByCourseId")
    public Result getAssignmentInfoByCourseId(Integer courseId) {
        List<AssignmentInfo> assignmentInfoList = assignmentInfoService.findAssignmentInfoByCourseId(courseId);
        if (assignmentInfoList == null) {
            return Result.error("1", "课程没有作业");
        } else {
            return Result.success(assignmentInfoList);
        }
    }



}

