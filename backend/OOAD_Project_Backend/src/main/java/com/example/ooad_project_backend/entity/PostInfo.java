package com.example.ooad_project_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.ooad_project_backend.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;


@Data // 自动生成get、set方法
@EqualsAndHashCode(callSuper = false)// 自动生成equals和 hashcode方法
@Accessors(chain = true)    // 允许链式访问，例如：myObject.setField1("value1").setField2(42)。
// 用于学生发布问题，或者学习心得
public class PostInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "post_id", type = IdType.AUTO)
    private Integer postId;

    private String postTitle;

    private String postContent;

    private Date time;

    private Integer postAuthor;

    private UserType authorType;

    private Integer courseId;

    private List<ReplyInfo> replyList;
}



