package com.example.storage.controller;

import com.example.storage.dto.FileDTO;
import com.example.storage.dto.PageDTO;
import com.example.storage.dto.ResponseMessage;
import com.example.storage.model.FileModel;
import com.example.storage.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
        fileService.putFile(file, comment);
        return ResponseEntity.ok().body(new ResponseMessage("File uploaded"));
    }

    @Operation(summary = "Возврат списка названий файлов", description = "Возвращает названия файлов")
    @GetMapping("/files/names")
    public ResponseEntity<List<String>> getFileName() {
        return ResponseEntity.ok().body(fileService.getFilesName());
    }

    @Operation(summary = "Возврат списка данных о файлах", description = "Возвращает данные о файлах")
    @GetMapping("/files")
    public ResponseEntity<List<?>> getAllInformationAboutFiles() {
        return ResponseEntity.ok().body(fileService.showAllFiles());
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
    public ResponseEntity<ResponseMessage> deleteFileByID(@PathVariable UUID id) {
        fileService.deleteFileByID(id);
        return ResponseEntity.ok().body(new ResponseMessage("File deleted"));
    }

    @Operation(summary = "Обновление/переименование файла", description = "Для обновления файла нужно указать его id и имя, на которое хотите переименовать")
    @PutMapping("/file/update/{id}")
    public ResponseEntity<ResponseMessage> updateFileByID(@RequestBody FileDTO fileDTO, @PathVariable("id") String id) {
        fileService.updateFile(fileDTO, id);
        return ResponseEntity.ok().body(new ResponseMessage("Файл обновлен " + fileDTO.getName()));
    }

    //todo сделать пагинацию для метода фильтрации | +-
    @Operation(summary = "Фильтрация файлов", description = "Возвращает список файлов, поля которых, совпадают с параметрами фильтрации")
    @Transactional
    @GetMapping("/files/filter")
    public ResponseEntity<PageDTO<FileDTO>> findFilesByName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "format", required = false) String format,
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        RequestParams requestParams = new RequestParams(name, format, from, to);
        return ResponseEntity.ok().body(fileService.findFilteredFiles(requestParams.getName(),
                requestParams.getFormat(),requestParams.getFrom(), requestParams.getTo()));
    }

    //todo один эндпоинт на все параметры фильтра /get?from=&to&name=&... | ++
}
