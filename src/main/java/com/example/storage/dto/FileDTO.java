package com.example.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private String id;
    private String fileName;
    private String downloadURL;
    private Long size;
    private String fileFormat;
    private LocalDateTime uploadDate;
    private LocalDateTime changeDate;
    /*private String comment;*/

    public FileDTO(String id, String fileName, String downloadURL, Long size, String fileFormat, LocalDateTime uploadDate) {
        this.id = id;
        this.fileName = fileName;
        this.downloadURL = downloadURL;
        this.size = size;
        this.fileFormat = fileFormat;
        this.uploadDate = uploadDate;
    }
}
