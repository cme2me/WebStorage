package com.example.storage.controller;

import com.example.storage.dto.FileDTO;
import com.example.storage.dto.ResponseMessage;
import com.example.storage.model.FileModel;
import com.example.storage.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "CRUD операции и поиск", description = "Взаимодействие с файлами")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Загрузка файла", description = "Необходимо выбрать файл и указать к нему комментарий")
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String comment) throws MultipartException {
        return fileService.putFile(file, comment);
    }

    @Operation(summary = "Возврат списка названий файлов", description = "Возвращает названия файлов")
    @GetMapping("/files/names")
    public ResponseEntity<List<String>> getFileName() {
        return fileService.getFilesName();
    }

    @Operation(summary = "Возврат списка данных о файлах", description = "Возвращает данные о файлах")
    @GetMapping("/files")
    public ResponseEntity<List<FileDTO>> getAllInformationAboutFiles() {
        return fileService.showAllFiles();
    }

    @Operation(summary = "Скачивания файла", description = "Метод возвращает ссылку на скачивание файла, необходимо указать id файла, который хотите скачать")
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFileByID(@PathVariable String id) {
        FileModel fileModel = fileService.downloadFileById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getName() + "")
                .body(fileModel.getData());
    }
    @GetMapping("/filter/{name}")
    public List<FileModel> fileModels(@PathVariable("name") String name) {
        return fileService.filterByName(name);
    }

    @Operation(summary = "Удаление файла", description = "Чтобы удалить файл, нужно указать его id")
    @PostMapping("/file/delete/{id}")
    public ResponseEntity<?> deleteFileByID(@PathVariable UUID id) {
        return fileService.deleteFileByID(id);
    }

    @Operation(summary = "Обновление/переименование файла", description = "Для обновления файла нужно указать его id и имя, на которое хотите переименовать")
    @PutMapping("/file/update/{id}")
    public ResponseEntity<ResponseMessage> updateFileByID(@RequestBody FileDTO fileDTO, @PathVariable("id") String id) {
        fileService.updateFile(fileDTO, id);
        return ResponseEntity.ok().body(new ResponseMessage("Файл обновлен " + fileDTO.getFileName()));
    }

    @Operation(summary = "Тестовое", description = "Тест")
    @GetMapping("/new/test")
    public ResponseEntity<List<FileDTO>> test() {
        return fileService.doSmtng();
    }
    //todo сделать пагинацию для метода фильтрации
    @Operation(summary = "Фильтрация файлов по их имени", description = "Возвращает список файлов, имя которых совпадает с указанным")
    @Transactional
    @GetMapping("/files/{name}")
    public ResponseEntity<List<FileDTO>> findFilesByName(@PathVariable("name") String name) {
        return fileService.findFilesByName(name);
    }

    @Operation(summary = "Фильтрация файлов по дате", description = "Возвращает список файлов, даты которых находятся между указанных")
    @Transactional
    @GetMapping("/files/date/{from}/{to}")
    public ResponseEntity<List<FileDTO>> findFilesByDate(@PathVariable("from") String from,
                                                         @PathVariable("to") String to) {
        return fileService.findFilesByDates(LocalDateTime.parse(from), LocalDateTime.parse(to));
    }

    //todo один эндпоинт на все параметры фильтра /get?from=&to&name=&...
}
