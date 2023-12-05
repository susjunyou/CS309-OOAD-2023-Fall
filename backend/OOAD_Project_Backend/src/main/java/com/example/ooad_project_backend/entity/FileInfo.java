package com.example.ooad_project_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String fileName;

    private String fileType;

    private byte[] fileData;

}
