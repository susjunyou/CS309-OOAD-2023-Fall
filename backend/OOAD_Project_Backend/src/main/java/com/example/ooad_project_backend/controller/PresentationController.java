package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.TeamInfo;
import com.example.ooad_project_backend.service.TeacherInfoService;
import com.example.ooad_project_backend.service.TeamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/presentation")
@CrossOrigin
public class PresentationController{

    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private TeamInfoService teamInfoService;

    @GetMapping("/findTeamInfoByProjectId")
    public Result findTeamInfoByProjectId(Integer projectId) {
        List<TeamInfo> teamInfos = teamInfoService.findAllTeamInfoByProjectId(projectId);
        if (teamInfos.size() == 0) {
            return Result.error("1", "no teams");
        } else {
            return Result.success(teamInfos);
        }
    }

    @PutMapping("/updatePresentationDateByTeamId")
    public Result updatePresentationDateByTeamId(Integer teamId, Date presentationDate) {
        return teamInfoService.updatePresentationDateByTeamId(teamId, presentationDate) ? Result.success() : Result.error();
    }



}

