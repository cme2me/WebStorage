package com.example.storage.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class File {
    private Long id;
    private Long date;
    private Long changeDate;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String comment;
}
