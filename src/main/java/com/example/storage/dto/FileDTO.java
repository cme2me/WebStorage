package com.example.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private UUID id;
    private String fileName;
    private String downloadURL;
    private int size;
    private String fileFormat;
    private LocalDateTime uploadDate;
    private LocalDateTime changeDate;
    private String comment;

    //todo lombok | done

}
