package com.example.storage.mapper;

import com.example.storage.dto.FileDTO;
import com.example.storage.model.FileModel;
import org.mapstruct.Mapper;

@Mapper
//todo сделать mapper
public interface ModelMapper {

    FileModel toModel(FileDTO dto);
}
