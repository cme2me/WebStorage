package com.example.storage.mapper;

import com.example.storage.dto.FileDTO;
import com.example.storage.model.FileModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
//todo сделать mapper
public interface EntityMapper {

    @Mapping(target = "size", expression = "java( fileModel.getData().length )")
    //todo отдельный метод для url
    /*@Mapping(target = "downloadURL", qualifiedByName = getDownloadURL method)*/
    FileDTO toFileDTO(FileModel fileModel);

    FileModel toEntity(FileDTO fileDTO);

    List<FileDTO> toFileDTOList(List<FileModel> fileModel);
}
