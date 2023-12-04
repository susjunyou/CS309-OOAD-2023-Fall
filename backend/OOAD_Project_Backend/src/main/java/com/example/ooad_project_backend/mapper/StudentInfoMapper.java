package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.StudentInfo;
import org.apache.ibatis.annotations.*;

import java.sql.Date;


@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {
    @Select("select * from student where account = #{account}")
    StudentInfo findStudentInfoByName(String account);

    @Select("select * from student where id = #{id}")
    StudentInfo findStudentInfoById(Integer id);

    @Select("select * from student where name = #{name}")
    StudentInfo findByName(@Param("name") String name);

    @Insert("INSERT INTO student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA, department, technology_stack, programming_skill, intended_teammate) " +
            "VALUES (#{id}, #{name}, #{account}, #{password}, #{email}, #{selfIntroduction}, #{phoneNumber}, #{sex}, #{major}, #{level}, false, #{department},#{technologyStack},#{programmingSkill},#{intendedTeammate})")
    void insertStudent(StudentInfo studentInfo);


    //自动填充提交时间为当前时间
    @Insert("insert into assignment_submission (student_id, assignment_id, content, submission_date, file_id) values (#{studentId},#{assignmentId},#{content},#{submitDate},#{fileId})")
    void submitAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate, Integer fileId);

    @Update("update assignment_submission set content = #{content}, submission_date = #{submitDate} where student_id = #{studentId} and assignment_id = #{assignmentId}")
    void updateAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate);

    @Insert("insert into project_submission (student_id, project_id, content, submission_date,file_id) values (#{studentId},#{projectId},#{content},#{submitDate},#{fileId})")
    void submitProject(Integer studentId, Integer projectId, String content, Date submitDate, Integer fileId);

    @Update("update student set password = #{password} where id = #{id}")
    void updatePassword(Integer id, String password);


    @Update("update student set email = #{email}, phone_number = #{phoneNumber}, self_introduction = #{selfIntroduction}, technology_stack =#{technologyStack}, programming_skill =#{programmingSkill}, intended_teammate =#{intendedTeammate} where id = #{id}")
    void updateStudentDetails(Integer id, String email, String phoneNumber, String selfIntroduction, String technologyStack, String programmingSkill, String intendedTeammate);

}
