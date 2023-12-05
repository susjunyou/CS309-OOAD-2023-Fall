package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.PostInfo;
import com.example.ooad_project_backend.entity.ReplyInfo;
import com.example.ooad_project_backend.mapper.PostMapper;
import com.example.ooad_project_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp extends ServiceImpl<PostMapper, PostInfo> implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public List<PostInfo> getPostsByCourseId(Integer courseId) {
        return postMapper.findPostByCourseId(courseId);
    }

    @Override
    public List<PostInfo> getPostsByStudentId(Integer studentId, Integer courseId) {
        return postMapper.findPostByStudentId(studentId, courseId);
    }

    @Override
    public List<PostInfo> getPostsByTeacherId(Integer teacherId, Integer courseId) {
        return postMapper.findPostByTeacherId(teacherId, courseId);
    }


    @Override
    public boolean releasePost(PostInfo postInfo) {
        postMapper.releasePost(postInfo.getPostTitle(), postInfo.getPostContent(), postInfo.getPostAuthor(), postInfo.getCourseId(), postInfo.getTime(), postInfo.getAuthorType());
        return true;
    }

    @Override
    public List<ReplyInfo> getReplyByPostId(Integer postId) {
        return postMapper.findReplyByPostId(postId);
    }

    @Override
    public boolean releaseReply(ReplyInfo replyInfo) {
        postMapper.releaseReply(replyInfo.getPostId(), replyInfo.getReplyAuthor(), replyInfo.getReplyContent(), replyInfo.getReplyTime(), replyInfo.getAuthorType());
        return true;
    }

    @Override
    public boolean deletePost(Integer postId) {
        postMapper.deletePost(postId);
        return true;
    }

    @Override
    public boolean deleteReply(Integer replyId) {
        postMapper.deleteReply(replyId);
        return true;
    }
}
