package com.example.ooad_project_backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.enums.UserType;
import org.apache.ibatis.annotations.*;

import java.lang.reflect.Type;
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

    @Insert("insert into assignment (assignment_title, assignment_description, assignment_deadline, assignment_status, max_score, proportion, releaser, releaser_type, course_id)" +
            " values (#{assignmentTitle}, #{assignmentDescription}, #{assignmentDeadline}, #{assignmentStatus}, #{maxScore}, #{proportion}, #{releaser}, #{releaserType}, #{courseId})")
    boolean addAssignment(String assignmentTitle, String assignmentDescription, Date assignmentDeadline, String assignmentStatus,
                          Integer maxScore, Double proportion, String releaser, UserType releaserType, Integer courseId);

    @Update("update assignment set assignment_title = #{assignmentTitle}, assignment_description = #{assignmentDescription}, assignment_deadline = #{assignmentDeadline}, assignment_status = #{assignmentStatus}, max_score = #{maxScore}, proportion = #{proportion}, releaser = #{releaser}, releaser_type = #{releaserType}, course_id = #{courseId} where id = #{assignmentId}")
    boolean updateAssignment(Integer assignmentId, String assignmentTitle, String assignmentDescription, Date assignmentDeadline, String assignmentStatus,
                             Integer maxScore, Double proportion, String releaser, UserType releaserType, Integer courseId);


    @Delete("delete from assignment where id = #{assignmentId}")
    boolean deleteById(Integer assignmentId);

}

