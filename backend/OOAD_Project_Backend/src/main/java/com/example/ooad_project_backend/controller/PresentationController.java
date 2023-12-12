package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.TeamInfo;
import com.example.ooad_project_backend.service.TeacherInfoService;
import com.example.ooad_project_backend.service.TeamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/findTeamInfoByTeamId")
    public Result findTeamInfoByTeamId(Integer teamId) {
        TeamInfo teamInfo = teamInfoService.findTeamInfoByTeamId(teamId);
        if (teamInfo == null) {
            return Result.error("1", "no such team");
        } else {
            return Result.success(teamInfo);
        }
    }


    @GetMapping("/addPresentation")
    public Result addPresentation(Integer teamId, Date presentationDate,Integer teacherId) {
        TeamInfo teamInfo = teamInfoService.findTeamInfoByTeamId(teamId);
        if (teamInfo == null) {
            return Result.error("1", "no such team");
        }
        if (teamInfo.getPresentationDate()==presentationDate){
            return Result.error("1", "presentation date already exists");
        }
        return teamInfoService.addPresentation(teamId, presentationDate,teacherId) ? Result.success() : Result.error();
    }

    @GetMapping("/deletePresentation")
    public Result deletePresentation(Integer teamId) {
        TeamInfo teamInfo = teamInfoService.findTeamInfoByTeamId(teamId);
        if (teamInfo == null) {
            return Result.error("1", "no such team");
        }
        if (teamInfo.getPresentationDate()==null){
            return Result.error("1", "presentation date doesn't exist");
        }
        return teamInfoService.deletePresentation(teamId) ? Result.success() : Result.error();
    }


    @GetMapping("/updatePresentation")
    public Result updatePresentationDateByTeamId(Integer teamId, Date presentationDate,Integer teacherId) {
        TeamInfo teamInfo = teamInfoService.findTeamInfoByTeamId(teamId);
        if (teamInfo == null) {
            return Result.error("1", "no such team");
        }
        if (teamInfo.getPresentationDate()==presentationDate){
            return Result.error("1", "presentation date is the same");
        }
        return teamInfoService.updatePresentationDateByTeamId(teamId, presentationDate,teacherId) ? Result.success() : Result.error();
    }



}

