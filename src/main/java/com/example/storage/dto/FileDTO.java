package com.example.storage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "storage_files")
@NoArgsConstructor
@Data
public class FileDTO {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String format;
    private Long uploadDate;
    private String changeDate;
    private String comment;
    @Lob
    private byte[] data;

    public FileDTO(String name, String format, byte[] data, Long uploadDate) {
        this.uploadDate = uploadDate;
        this.name = name;
        this.format = format;
        this.data = data;
    }
}
