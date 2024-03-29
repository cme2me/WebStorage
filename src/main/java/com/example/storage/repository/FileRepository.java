package com.example.storage.repository;

import com.example.storage.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID>, JpaSpecificationExecutor<FileEntity> /*todo сделать спеку на фильтр | + */, FileCustomRepository {
    @Query(value = "select sf.name from storage_files sf", nativeQuery = true)
    List<String> findAllNames();
}
