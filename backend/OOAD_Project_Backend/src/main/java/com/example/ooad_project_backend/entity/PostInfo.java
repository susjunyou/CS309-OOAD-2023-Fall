package com.example.ooad_project_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


// 用于学生发布问题，或者学习心得
public class PostInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "post_id", type = IdType.AUTO)
    private Integer postId;

    private String postTitle;

    private String postContent;


    private UserInfo postAuthor;

    private List<replyInfo> replyList;
}

class replyInfo {
    private UserInfo replyAuthor;

    private String replyContent;
}

