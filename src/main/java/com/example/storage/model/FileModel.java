package com.example.storage.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "storage_files")
@NoArgsConstructor
@Data
public class FileModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String format;
    @Lob
    private byte[] data;
    private LocalDateTime date;

    public FileModel(String name, String format, byte[] data, LocalDateTime date) {
        this.name = name;
        this.format = format;
        this.data = data;
        this.date = date;
    }
}
