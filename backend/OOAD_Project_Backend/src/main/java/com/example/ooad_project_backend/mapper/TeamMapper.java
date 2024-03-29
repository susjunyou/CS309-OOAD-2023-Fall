package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.JoinTeamInfo;
import com.example.ooad_project_backend.entity.TeamInfo;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface TeamMapper extends BaseMapper<TeamInfo> {


    @Update("update team set team_name = #{teamName}, leader = #{leader}, team_description = #{teamDescription}, team_size = #{teamSize},recruitment_information = #{recruitmentInformation}, privacy_team = #{privacyTeam} where team_id = #{teamId}")
    void updateTeamInfo(TeamInfo teamInfo);

    @Update("update team set leader = #{studentId} where team_id = #{teamId}")
    void updateTeamLeader(Integer teamId, Integer studentId);

    @Insert("insert into team(team_name, leader, team_description, team_size, project_id,recruitment_information,privacy_team)" +
            " VALUES (#{teamName}, #{leader}, #{teamDescription}, #{teamSize}, #{projectId},#{recruitmentInformation},#{privacyTeam})")
    void createTeam(TeamInfo teamInfo);


    @Update("update team set leader = #{studentId} where team_id = #{teamId}")
    void setLeader(Integer teamId, Integer studentId);

    @Insert("insert into team_student(team_id, student_id, project_id) VALUES (#{teamId}, #{studentId}, #{projectId})")
    void joinTeam(Integer teamId, Integer studentId, Integer projectId);

    @Insert("insert into invite_join_team(team_id, student_id,project_id) VALUES (#{teamId}, #{studentId}, #{projectId})")
    void addInviteJoinTeam(Integer teamId, Integer studentId, Integer projectId);

    @Delete("delete from team_student where team_id = #{teamId} and student_id = #{studentId}")
    void leaveTeam(Integer teamId, Integer studentId);

    @Delete("delete from team where team_id = #{teamId}")
    void deleteTeamInTeam(Integer teamId);

    @Delete("delete from team_student where team_id = #{teamId}")
    void deleteTeamInTeamStudent(Integer projectId);

    @Select("select * from team where team_id = #{teamId}")
    TeamInfo findTeamInfoByTeamId(Integer teamId);

    @Select("select team_id from team_student where student_id = #{studentId} and project_id  = #{projectId}")
    Integer findTeamIdByProjectIdAndStudentId(Integer projectId, Integer studentId);

//    @Select("select team_id from team where project_id =#{projectId} and leader =#{leader} ")
//    List<TeamInfo> findTeamIdByProjectIdAndLeader(Integer projectId, Integer leader);

    @Select("select student_id from team_student where project_id = #{projectId} and team_id = #{teamId}")
    List<Integer> findStudentIdsByTeamIdAndProjectId(Integer teamId, Integer projectId);

    @Select("select * from team where project_id = #{projectId} and leader = #{leader}")
    TeamInfo findTeamInfoByProjectIdAndLeader(Integer projectId, Integer leader);

    @Select("select * from team where project_id = #{projectId}")
    List<TeamInfo> findTeamInfoByProjectId(Integer projectId);

    @Select("select student_id from team_student where team_id = #{teamId}")
    List<Integer> findStudentIdsByTeamId(Integer teamId);

    @Select("select * from request_join_team where team_id = #{teamId}")
    List<JoinTeamInfo> getRequestsStudentIdByTeamId(Integer teamId);

    @Select("select * from invite_join_team where student_id = #{studentId}")
    List<JoinTeamInfo> getInvitesJoinTeam(Integer studentId);


    //team_id在team_student表里通过student_id找到，然后在team表里通过team_id找到
    @Select("select * from team where team_id in (select team_id from team_student where student_id = #{studentId})")
    List<TeamInfo> findTeamInfoByStudentId(Integer studentId);

    @Insert("insert into request_join_team (team_id, student_id,project_id) VALUES (#{teamId}, #{studentId}, #{projectId})")
    void requestJoinTeam(Integer teamId, Integer studentId, Integer projectId);

    @Select("select student_id from request_join_team where id = #{requestId}")
    Integer findStudentIdByRequestId(Integer requestId);

    @Select("select team_id from request_join_team where student_id = #{studentId}")
    List<Integer> findTeamIdByStudentIdInRequest(Integer studentId);

    @Select("select team_id from request_join_team where id = #{requestId}")
    Integer findTeamIdByRequestId(Integer requestId);

    @Select("select student_id from invite_join_team where id = #{inviteId}")
    Integer findStudentIdByInviteId(Integer inviteId);

    @Select("select team_id from invite_join_team where id = #{inviteId}")
    Integer findTeamIdByInviteId(Integer inviteId);

    @Delete("delete from invite_join_team where id = #{inviteId}")
    void deleteInvite(Integer inviteId);

    @Select("select team_id from invite_join_team where student_id = #{studentId}")
    List<Integer> findTeamIdByStudentIdInInvite(Integer studentId);

    @Select("select project_id from team where team_id = #{teamId}")
    Integer findProjectIdByTeamId(Integer teamId);

    @Delete("delete from request_join_team where id = #{requestId}")
    void deleteRequest(Integer requestId);

    @Update("update team set presentation_date = #{presentationDate},teacher_id = #{teacherId} where team_id = #{teamId}")
    void updatePresentationDateByTeamId(Integer teamId, Date presentationDate, Integer teacherId);

    @Select("select student_id from team_student where project_id = #{projectId}")
    List<Integer> findAlreadyJoinedTeamStudentIdsByProjectId(Integer projectId);

    @Update("update team set presentation_date = #{presentationDate},teacher_id = #{teacherId} where team_id = #{teamId}")
    void addPresentation(Integer teamId, Date presentationDate, Integer teacherId);

    @Update("update team set presentation_date = null where team_id = #{teamId}")
    void deletePresentation(Integer teamId);

    @Update("update team set privacy_team = #{privacyTeam} where team_id = #{teamId}")
    void modifyIsPublic(Integer teamId, boolean privacyTeam);

    @Delete("delete from invite_join_team where student_id = #{studentId}")
    void deleteInviteByStudentId(Integer studentId);

    @Delete("delete from request_join_team where student_id = #{studentId}")
    void deleteRequestByStudentId(Integer studentId);
}
