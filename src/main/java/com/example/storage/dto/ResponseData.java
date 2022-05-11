package com.example.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseData {
    private String fileName;
    private String downloadURL;
    private Long size;
    private String fileFormat;
    private LocalDateTime uploadDate;
    /*private String changeDate;
    private String comment;*/
}
