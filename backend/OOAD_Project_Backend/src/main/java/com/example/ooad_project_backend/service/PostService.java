package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.PostInfo;
import com.example.ooad_project_backend.entity.ReplyInfo;

import java.util.List;

public interface PostService extends IService<PostInfo> {

    List<PostInfo> getPostsByCourseId(Integer courseId);

    List<PostInfo> getPostsByStudentId(Integer studentId, Integer courseId);

    List<PostInfo> getPostsByTeacherId(Integer teacherId, Integer courseId);

    boolean releasePost(PostInfo postInfo);

    List<ReplyInfo> getReplyByPostId(Integer postId);

    boolean releaseReply(ReplyInfo replyInfo);

    boolean deletePost(Integer postId);

    boolean deleteReply(Integer replyId);


}
