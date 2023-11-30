package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.PostInfo;
import com.example.ooad_project_backend.entity.ReplyInfo;
import com.example.ooad_project_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;
    @GetMapping("/getByCourseId")
    public Result getPostByCourseId(Integer courseId) {
        List<PostInfo> postInfos = postService.getPostsByCourseId(courseId);
        if (postInfos.size() == 0) {
            return Result.error("1", "没有找到该课程的帖子");
        }
        return Result.success(postInfos);
    }

    @GetMapping("/getByStudentId")
    public Result getPostByStudentId(Integer studentId, Integer courseId) {
        List<PostInfo> postInfos = postService.getPostsByStudentId(studentId, courseId);
        if (postInfos.size() == 0) {
            return Result.error("1", "没有找到该学生的帖子");
        }
        return Result.success(postInfos);
    }

    @GetMapping("/getByTeacherId")
    public Result getPostByTeacherId(Integer teacherId, Integer courseId) {
        List<PostInfo> postInfos = postService.getPostsByTeacherId(teacherId, courseId);
        if (postInfos.size() == 0) {
            return Result.error("1", "没有找到该教师的帖子");
        }
        return Result.success(postInfos);
    }

    @PostMapping("/releasePost")
    public Result releasePost(PostInfo postInfo) {
        boolean flag = postService.releasePost(postInfo);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "发布失败");
        }
    }

    @PostMapping("/releaseReply")
    public Result releaseReply(ReplyInfo replyInfo) {
        boolean flag = postService.releaseReply(replyInfo);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "发布失败");
        }
    }

    @GetMapping("/getReplyByPostId")
    public Result getReplyByPostId(Integer postId) {
        List<ReplyInfo> replyInfos = postService.getReplyByPostId(postId);
        if (replyInfos.size() == 0) {
            return Result.error("1", "没有找到该帖子的回复");
        }
        return Result.success(replyInfos);
    }

}
