package com.example.storage.mapper;


import com.example.storage.dto.FileDTO;
import com.example.storage.dto.PageDTO;
import com.example.storage.model.FileModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
//todo сделать mapper | +--
public interface EntityMapper {
    @Mapping(target = "size", expression = "java( fileModel.getData().length )")
    @Mapping(target = "downloadURL", qualifiedByName = "createDownloadURL", source = "fileModel")
    FileDTO toFileDTO(FileModel fileModel);

    FileModel toEntity(FileDTO fileDTO);

    List<FileDTO> toFileDTOList(List<FileModel> fileModel);

    @Named("createDownloadURL")
    default String createDownloadURL(FileModel fileModel) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileModel.getId().toString())
                .toUriString();
    }

    @Mapping(target = "PageDTO", source = "Page")
    default PageDTO<FileDTO> toPageDTO(Page<FileModel> fileModelPage) {
        int totalPages = fileModelPage.getTotalPages();
        long totalElements = fileModelPage.getTotalElements();
        int size = fileModelPage.getSize();
        List<FileModel> content = fileModelPage.getContent();
        PageDTO<FileDTO> fileDTOPageDTO = new PageDTO<>();
        fileDTOPageDTO.setSize(size);
        fileDTOPageDTO.setTotalPages(totalPages);
        fileDTOPageDTO.setTotalElements(totalElements);
        fileDTOPageDTO.setContent(toFileDTOList(content));
        return fileDTOPageDTO;
    }
}
