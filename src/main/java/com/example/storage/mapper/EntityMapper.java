package com.example.storage.mapper;


import com.example.storage.dto.FileDTO;
import com.example.storage.model.FileModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
//todo сделать mapper
public interface EntityMapper {
    @Mapping(target = "size", expression = "java( fileModel.getData().length )")
    @Mapping(target = "downloadURL", expression = "java( createDownloadURL(fileModel) )")
    FileDTO toFileDTO(FileModel fileModel);

    FileModel toEntity(FileDTO fileDTO);

    List<FileDTO> toFileDTOList(List<FileModel> fileModel);

    @Named("createDownloadURL")
    default String createDownloadURL(FileModel fileModel) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(String.valueOf(fileModel.getId()))
                .toUriString();
    }
}
