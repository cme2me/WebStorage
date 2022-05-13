package com.example.storage.mapper;

import com.example.storage.dto.FileDTO;
import com.example.storage.model.FileModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FileMapper {
    void updateFileFromDTO(FileDTO fileDTO, @MappingTarget FileModel fileModel);
}
