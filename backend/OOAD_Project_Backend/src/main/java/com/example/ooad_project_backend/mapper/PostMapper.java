package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.PostInfo;
import com.example.ooad_project_backend.entity.ReplyInfo;
import com.example.ooad_project_backend.enums.UserType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<PostInfo> {

    @Select("select * from post where course_id = #{courseId}")
    List<PostInfo> findPostByCourseId(Integer courseId);

    @Select("select * from post where post_author = #{studentId} and author_type = 'STUDENT' and course_id = #{courseId}")
    List<PostInfo> findPostByStudentId(Integer studentId, Integer courseId);

    @Select("select * from post where post_author = #{teacherId} and author_type = 'TEACHER' and course_id = #{courseId}")
    List<PostInfo> findPostByTeacherId(Integer teacherId, Integer courseId);

    @Insert("insert into post(post_title, post_content, post_author, author_type, time, course_id) " +
            "values (#{postTitle}, #{postContent}, #{userId}, #{userType}, #{date}, #{courseId})")
    void releasePost(String postTitle, String postContent, Integer userId, Integer courseId, Date date, UserType userType);

    @Select(" select * from reply where post_id = #{postId}")
    List<ReplyInfo> findReplyByPostId(Integer postId);

    @Select("insert into reply(reply_content, reply_author, reply_time, post_id, author_type) VALUES (#{replyContent},#{userId},#{replyTime},#{postId},#{userType})")
    void releaseReply(Integer postId, Integer userId, String replyContent, Date replyTime, UserType userType);

    @Select("delete from post where post_id = #{postId}")
    void deletePost(Integer postId);

    @Select("delete from reply where reply_id = #{replyId}")
    void deleteReply(Integer replyId);
}
