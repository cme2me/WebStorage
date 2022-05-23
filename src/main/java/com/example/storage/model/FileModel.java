package com.example.storage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "storage_files")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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
    private LocalDateTime updatedDate;
    private String comment;

    public FileModel(String name, String format, byte[] data, LocalDateTime date, LocalDateTime updatedDate, String comment) {
        this.name = name;
        this.updatedDate = updatedDate;
        this.format = format;
        this.data = data;
        this.date = date;
        this.comment = comment;
    }
}
