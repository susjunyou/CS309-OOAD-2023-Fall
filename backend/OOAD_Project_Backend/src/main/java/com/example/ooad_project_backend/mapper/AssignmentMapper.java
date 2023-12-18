package com.example.ooad_project_backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.enums.StatusType;
import com.example.ooad_project_backend.enums.UserType;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface AssignmentMapper extends BaseMapper<AssignmentInfo> {

    @Select("select * from assignment where course_id = #{courseId}")
    List<AssignmentInfo> findAllAssignmentInfoByCourseId(Integer courseId);

    @Select("select * from assignment where id = #{assignmentId}")
    AssignmentInfo findAssignmentInfoByAssignmentId(Integer assignmentId);

    @Select("select * from assignment_submission where student_id = #{studentId} and assignment_id = #{courseId}")
    List<AssignmentInfo> findAssignmentInfoByStudentIdAndCourseId(Integer studentId, Integer courseId);

    @Delete("delete from file where id = #{fileId}")
    boolean deleteFileById(Integer fileId);

    @Insert("insert into assignment (assignment_title, assignment_description, assignment_deadline, assignment_status, max_score, proportion, releaser, releaser_type, course_id,file_id)" +
            " values (#{assignmentTitle}, #{assignmentDescription}, #{assignmentDeadline}, #{assignmentStatus}, #{maxScore}, #{proportion}, #{releaser}, #{releaserType}, #{courseId}), #{fileId} ")
    boolean addAssignment(String assignmentTitle, String assignmentDescription, Date assignmentDeadline, StatusType assignmentStatus,
                          Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId,Integer fileId);

    @Update("update assignment set assignment_title = #{assignmentTitle}, assignment_description = #{assignmentDescription}, assignment_deadline = #{assignmentDeadline}, assignment_status = #{assignmentStatus}, max_score = #{maxScore}, proportion = #{proportion}, releaser = #{releaser}, releaser_type = #{releaserType}, course_id = #{courseId}, file_id =#{fileId} where id = #{assignmentId}")
    boolean updateAssignment(Integer assignmentId, String assignmentTitle, String assignmentDescription, Date assignmentDeadline, StatusType assignmentStatus,
                             Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId,Integer fileId);


    @Delete("delete from assignment where id = #{assignmentId}")
    boolean deleteById(Integer assignmentId);

}

