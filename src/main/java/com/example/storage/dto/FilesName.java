package com.example.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//todo обект FilesName из контроллеров будет возвращаться один | +?
public class FilesName {
    //todo сделать List<String> | Done
    private List<String> filename;
}
