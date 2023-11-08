package com.example.ooad_project_backend.entity;

import com.example.ooad_project_backend.enums.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data // 自动生成get、set方法
@EqualsAndHashCode(callSuper = false)// 自动生成equals和 hashcode方法
@Accessors(chain = true)    // 允许链式访问，例如：myObject.setField1("value1").setField2(42)。
public class UserInfo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long id;

    private Integer level;

    private String email;

    private String phoneNumber;

    private boolean sex;// True:male, False:female

    private String username;

    private String password;

    private UserType userType;

    private String selfIntroduction;


}
