package com.example.storage.dto;

import java.time.LocalDateTime;

public class FilterVariable {
    private String name;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    private String fileType;

    public FilterVariable(String name, LocalDateTime timeFrom, LocalDateTime timeTo, String fileType) {
        this.name = name;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalDateTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalDateTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalDateTime timeTo) {
        this.timeTo = timeTo;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
