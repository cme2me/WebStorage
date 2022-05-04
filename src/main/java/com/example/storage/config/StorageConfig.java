package com.example.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
@ConfigurationProperties(prefix = "file")
@Data
@Entity
@Table(name = "storage")
@Configuration
public class StorageConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_change_date")
    private String changeDate;
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "file_format")
    private String fileFormat;
    @Column(name = "file_upload_date")
    private String uploadDate;
    @Column(name = "file_comment")
    private String comment;
    @Column(name = "upload_dir")
    private String uploadDir;
}
