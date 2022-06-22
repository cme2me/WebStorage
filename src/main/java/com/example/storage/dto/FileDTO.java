package com.example.storage.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private UUID id;
    private String name;
    private String downloadURL;
    private int size;
    private String format;
    private LocalDateTime date;
    private LocalDateTime updatedDate;
    private String comment;

    //todo lombok | done

}
