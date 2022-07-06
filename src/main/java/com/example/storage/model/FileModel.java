package com.example.storage.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "storage_files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FileModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //todo сделать тип UUID | +
    private UUID id;
    private String name;
    private String format;
    @Lob
    private byte[] data;
    @CreationTimestamp
    private LocalDateTime date;
    //todo проверить автогенерацию дат | +
    @UpdateTimestamp
    private LocalDateTime updatedDate;
    private String comment;

    /*@OneToOne
    private SeconModel seconModel;*/

    public FileModel(String name, String format, byte[] data, String comment) {
        this.name = name;
        this.format = format;
        this.data = data;
        this.comment = comment;
    }
}
