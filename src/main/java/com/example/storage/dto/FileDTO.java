package com.example.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FileDTO {
    private String id;
    private String fileName;
    private String downloadURL;
    private Long size;
    private String fileFormat;
    private LocalDateTime uploadDate;
    /*private String changeDate;
    private String comment;*/
}
