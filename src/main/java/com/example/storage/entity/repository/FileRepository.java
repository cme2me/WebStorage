package com.example.storage.entity.repository;

import com.example.storage.config.StorageConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<StorageConfig, Long> {
    @Query("Select s from StorageConfig s where file_id = ?1 and file_format = ?2")
    StorageConfig checkFileByID(Long fileId, String fileType);

    @Query("Select fileName from StorageConfig a where file_id = ?1 and file_format = ?2")
    String getUploadFilePath(Long fileID, String fileType);
}
