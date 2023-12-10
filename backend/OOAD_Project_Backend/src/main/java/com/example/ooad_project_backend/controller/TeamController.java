package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.JoinTeamInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeamInfo;
import com.example.ooad_project_backend.service.TeamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/team")
@CrossOrigin
public class TeamController {
    @Autowired
    private TeamInfoService teamInfoService;

    @GetMapping("/updateTeamInfo")
    public Result updateTeamInfo(TeamInfo teamInfo) {
        return teamInfoService.updateTeamInfo(teamInfo) ? Result.success() : Result.error();
    }

    @GetMapping("/updateTeamLeader")
    public Result updateTeamLeader(Integer teamId, Integer studentId) {
        return teamInfoService.updateTeamLeader(teamId, studentId) ? Result.success() : Result.error();
    }

    @GetMapping("/create")
    public Result create(TeamInfo teamInfo) {
        System.out.println(teamInfo.toString());
        return teamInfoService.createTeam(teamInfo) ? Result.success() : Result.error();
    }

    @GetMapping("/join")
    public Result join(Integer studentId, TeamInfo teamInfo) {
        System.out.println(teamInfo.toString());
        return teamInfoService.joinTeam(teamInfo, studentId) ? Result.success() : Result.error();
    }

    @GetMapping("/leave")
    public Result leave(Integer studentId, Integer teamId) {
        return teamInfoService.leaveTeam(teamId, studentId) ? Result.success() : Result.error();
    }

    @GetMapping("/delete")
    public Result delete(Integer teamId) {
        return teamInfoService.deleteTeam(teamId) ? Result.success() : Result.error();
    }

    @GetMapping("/findTeamInfoByStudentId")
    public Result findTeamInfoByStudentId(Integer studentId) {
        List<TeamInfo> teamInfos = teamInfoService.findAllTeamInfoByStudentId(studentId);
        if (teamInfos.size() == 0) {
            return Result.error("1", "no teams");
        } else {
            return Result.success(teamInfos);
        }
    }


    @GetMapping("/manageTeamRequest")
    public Result manageTeamRequest(Integer requestId, boolean isAccepted) {
        return teamInfoService.manageTeamRequest(requestId, isAccepted) ? Result.success() : Result.error();
    }

    @GetMapping("/requestJoinTeam")
    public Result requestJoinTeam(Integer studentId, Integer teamId, Integer projectId) {
        return teamInfoService.requestJoinTeam(teamId, studentId, projectId) ? Result.success() : Result.error();
    }

    @GetMapping("/getRequestsJoinTeam")
    public Result getRequestsJoinTeam(Integer teamId) {
        List<JoinTeamInfo> studentInfos = teamInfoService.getRequestsJoinTeam(teamId);
        if (studentInfos.size() == 0) {
            return Result.error("1", "no requests");
        } else {
            return Result.success(studentInfos);
        }
    }


    @GetMapping("/findTeamInfoByProjectId")
    public Result findTeamInfoByProjectId(Integer projectId) {
        List<TeamInfo> teamInfos = teamInfoService.findAllTeamInfoByProjectId(projectId);
        if (teamInfos.size() == 0) {
            return Result.error("1", "no teams");
        } else {
            return Result.success(teamInfos);
        }
    }

    @GetMapping("/findTeamMembers")
    public Result findTeamNumberByTeamId(Integer teamId) {
        List<StudentInfo> studentInfos = teamInfoService.findStudentInfoByTeamId(teamId);
        if (studentInfos.size() == 0) {
            return Result.error("1", "no team");
        } else {
            return Result.success(studentInfos);
        }
    }


    @GetMapping("/manageInvite")
    public Result manageInvite(Integer id, boolean isAccepted) {
        return teamInfoService.manageInvite(id, isAccepted) ? Result.success() : Result.error();
    }

    @GetMapping("/invite")
    public Result invite(Integer studentId, Integer teamId, Integer projectId) {
        return teamInfoService.inviteStudent(teamId, studentId, projectId) ? Result.success() : Result.error();
    }

    @GetMapping("/getInvites")
    public Result getInvites(Integer studentId, Integer projectId) {
        List<JoinTeamInfo> teamInfos = teamInfoService.getInvitesJoinTeam(studentId);
        List<JoinTeamInfo> myTeamInfo = new ArrayList<>();
        for (JoinTeamInfo teamInfo : teamInfos) {
            if (teamInfo.getProjectId().equals(projectId)) {
                myTeamInfo.add(teamInfo);
            }
        }
        if (myTeamInfo.size() == 0) {
            return Result.error("1", "no invites");
        } else {
            return Result.success(myTeamInfo);
        }
    }

    @GetMapping("/findTeamInfoByTeamId")
    public Result findTeamInfoByTeamId(Integer teamId) {
        TeamInfo teamInfo = teamInfoService.findTeamInfoByTeamId(teamId);
        if (teamInfo == null) {
            return Result.error("1", "no team");
        } else {
            return Result.success(teamInfo);
        }
    }

    @GetMapping("/getStudentNotJoinTeam")
    public Result getStudentNotJoinTeam(Integer projectId, Integer courseId) {
        List<StudentInfo> studentInfos = teamInfoService.findStudentNotJoinTeam(projectId, courseId);
        if (studentInfos.size() == 0) {
            return Result.error("1", "no students");
        } else {
            return Result.success(studentInfos);
        }
    }


}
