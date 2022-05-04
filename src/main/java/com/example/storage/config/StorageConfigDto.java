package com.example.storage.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class StorageConfigDto implements Serializable {
    private final Long id;
    private final String fileName;
    private final String changeDate;
    private final Long fileSize;
    private final String fileFormat;
    private final String uploadDate;
    private final String comment;
    private final String uploadDir;
}
