package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.StudentInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;


@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {
    @Select("select * from student where account = #{account}")
    StudentInfo findStudentInfoByName(String account);

    @Select("select * from student where id = #{id}")
    StudentInfo findStudentInfoById(Integer id);

    @Select("select * from student where name = #{name}")
    StudentInfo findByName(@Param("name") String name);

    @Insert("INSERT INTO student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA, department) " +
            "VALUES (#{id}, #{name}, #{account}, #{password}, #{email}, #{selfIntroduction}, #{phoneNumber}, #{sex}, #{major}, #{level}, false, #{department})")
    void insertStudent(StudentInfo studentInfo);

    @Insert("insert into assignment_submission (student_id, assignment_id, content, submission_date) values (#{studentId},#{assignmentId},#{content},#{submitDate})")
    void submitAssignment(Integer studentId, Integer assignmentId, String content, Date submitDate);

    @Insert("insert into project_submission (student_id, project_id, content, submission_date) values (#{studentId},#{projectId},#{content},#{submitDate})")
    void submitProject(Integer studentId, Integer projectId, String content, Date submitDate);

}
