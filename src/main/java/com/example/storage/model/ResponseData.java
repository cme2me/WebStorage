package com.example.storage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ResponseData {
    private String fileName;
    private String downloadURL;
    private Long size;
    private String fileFormat;
    private String uploadDate;
    private String changeDate;
    private String comment;
}
