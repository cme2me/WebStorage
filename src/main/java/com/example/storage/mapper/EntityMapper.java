package com.example.storage.mapper;


import com.example.storage.dto.FileDTO;
import com.example.storage.dto.PageDTO;
import com.example.storage.model.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
//todo сделать mapper | +
public interface EntityMapper {
    @Mapping(target = "size", expression = "java( fileEntity.getData().length )")
    @Mapping(target = "downloadURL", qualifiedByName = "createDownloadURL", source = "fileEntity")
    FileDTO toFileDTO(FileEntity fileEntity);

    List<FileDTO> toFileDTOList(List<FileEntity> fileEntity);

    @Named("createDownloadURL")
    default String createDownloadURL(FileEntity fileEntity) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileEntity.getId().toString())
                .toUriString();
    }

    default PageDTO<FileDTO> toPageDTO(Page<FileEntity> fileModelPage) {
        PageDTO<FileDTO> fileDTOPageDTO = new PageDTO<>();
        fileDTOPageDTO.setSize(fileModelPage.getSize());
        fileDTOPageDTO.setTotalPages(fileModelPage.getTotalPages());
        fileDTOPageDTO.setTotalElements(fileModelPage.getTotalElements());
        fileDTOPageDTO.setContent(toFileDTOList(fileModelPage.getContent()));
        return fileDTOPageDTO;
    }
}
