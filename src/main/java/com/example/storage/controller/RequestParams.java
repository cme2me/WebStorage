package com.example.storage.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RequestParams {
    private String name;
    private String format;
    private LocalDateTime from;
    private LocalDateTime to;
}
