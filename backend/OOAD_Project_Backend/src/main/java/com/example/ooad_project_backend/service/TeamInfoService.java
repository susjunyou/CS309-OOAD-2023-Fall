package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.JoinTeamInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeamInfo;
import com.example.ooad_project_backend.entity.TeamPeerRevisionInfo;

import java.sql.Date;
import java.util.List;

public interface TeamInfoService extends IService<TeamInfo> {

    boolean updateTeamInfo(TeamInfo teamInfo);

    boolean updateTeamLeader(Integer teamId, Integer studentId);

    boolean createTeam(TeamInfo teamInfo);

    boolean joinTeam(TeamInfo teamInfo, Integer studentId);

    List<TeamInfo> findAllTeamInfoByProjectId(Integer projectId);

    List<StudentInfo> findStudentInfoByTeamId(Integer teamId);

    TeamInfo findTeamInfoByTeamId(Integer teamId);

    boolean leaveTeam(Integer teamId, Integer studentId);

    boolean deleteTeam(Integer teamId);

    List<TeamInfo> findAllTeamInfoByStudentId(Integer studentId);

    boolean requestJoinTeam(Integer teamId, Integer studentId, Integer projectId);

    List<JoinTeamInfo> getRequestsJoinTeam(Integer teamId);

    boolean manageTeamRequest(Integer requestId, boolean isAccepted);

    boolean manageInvite(Integer id, boolean isAccepted);

    boolean inviteStudent(Integer teamId, Integer studentId, Integer projectId);

    List<JoinTeamInfo> getInvitesJoinTeam(Integer studentId);

    boolean updatePresentationDateByTeamId(Integer teamId, Date presentationDate, Integer teacherId);

    List<StudentInfo> findStudentNotJoinTeam(Integer projectId, Integer courseId);

    boolean addPresentation(Integer teamId, Date presentationDate, Integer teacherId);

    boolean deletePresentation(Integer teamId);

    List<TeamPeerRevisionInfo> findTeamPeerRevision(Integer teamId, Integer projectId);

    boolean updateTeamPeerRevision(TeamPeerRevisionInfo teamPeerRevisionInfo);

    List<TeamInfo> findAlreadyPeerGradeTeam(Integer teamId, Integer projectId);

    List<TeamInfo> findNotYetPeerGradeTeam(Integer teamId, Integer projectId);

    boolean modifyIsPublic(Integer teamId, boolean isPublic);
}
