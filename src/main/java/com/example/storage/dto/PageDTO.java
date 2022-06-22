package com.example.storage.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDTO<T> {
    private long size;
    private long totalElements;
    private int totalPages;
    private List<T> content;
}
