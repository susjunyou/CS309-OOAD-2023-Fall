package com.example.ooad_project_backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileInfo {

    private Integer id;

    private String fileName;

    private String fileType;

    private byte[] fileData;

}
