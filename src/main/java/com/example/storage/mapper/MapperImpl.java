/*package com.example.storage.mapper;

import com.example.storage.dto.FileDTO;
import com.example.storage.model.FileModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperImpl implements EntityMapper {

    @Override
    public FileDTO toFileDTO(FileModel fileModel) {
        String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(String.valueOf(fileModel.getId()))
                .toUriString();
        FileDTO fileDTO = new FileDTO();
        fileDTO.setDownloadURL(fileDownloadURL);
        fileDTO.setSize(fileModel.getData().length);
        fileDTO.setFormat(fileModel.getFormat());
        fileDTO.setName(fileModel.getName());
        fileDTO.setDate(fileModel.getDate());
        fileDTO.setId(fileModel.getId());
        fileDTO.setUpdatedDate(fileModel.getUpdatedDate());
        fileDTO.setComment(fileModel.getComment());
        return fileDTO;
    }

    @Override
    public FileModel toEntity(FileDTO fileDTO) {
        FileModel fileModel = new FileModel();
        fileModel.setComment(fileDTO.getComment());
        fileModel.setName(fileDTO.getName());
        fileModel.setUpdatedDate(fileDTO.getUpdatedDate());
        fileModel.setFormat(fileDTO.getFormat());
        fileModel.setDate(fileDTO.getDate());
        fileModel.setId(fileDTO.getId());
        return fileModel;
    }

    @Override
    public List<FileDTO> toFileDTOList(List<FileModel> fileModels) {
        List<FileDTO> fileDTOList = new ArrayList<>(fileModels.size());
        for (FileModel model : fileModels) {
            fileDTOList.add(toFileDTO(model));
        }
        return fileDTOList;
    }
}*/
