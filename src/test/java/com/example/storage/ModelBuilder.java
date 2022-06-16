package com.example.storage;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
@Getter
public class ModelBuilder {
    private  byte[] fileBytes = Files.readAllBytes(Paths.get("src/test/resources/1.txt"));
    private String id = "123442";
    private String name = "1.txt";
    private String comment = "testFileComment";
    private LocalDateTime uploadDate = LocalDateTime.now();
    private LocalDateTime updatedDate = LocalDateTime.now();
    private String format = "text/txt";

    public ModelBuilder() throws IOException {
    }
}
