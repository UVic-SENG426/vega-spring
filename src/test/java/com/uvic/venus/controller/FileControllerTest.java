package com.uvic.venus.controller;

import com.uvic.venus.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FileControllerTest {


    private FileController fileController;
    private StorageService mockStorageService;


    @BeforeEach
    void setUp() {
        mockStorageService = Mockito.mock(StorageService.class);
        this.fileController = new FileController();
        this.fileController.setStorageService(mockStorageService);
    }

    @Test
    void listUploadedFiles() {
        List<Path> pathList = new ArrayList<>();
        pathList.add(Paths.get("/home/user/Documents/doc1"));
        pathList.add(Paths.get("/home/user/Documents/doc2"));
        pathList.add(Paths.get("/home/user/Documents/doc3"));
        List<String> stringList = pathList.stream().map(path -> path.getFileName().toString()).collect(Collectors.toList());
        Mockito.when(mockStorageService.loadAll()).thenReturn(pathList.stream());
        ResponseEntity<?> response = fileController.listUploadedFiles();
        assertEquals(ResponseEntity.ok(stringList), response);
        Mockito.verify(mockStorageService).loadAll();
    }

    @Test
    void serveFile() {
        Resource file = Mockito.mock(Resource.class);
        Mockito.when(mockStorageService.loadAsResource("filename")).thenReturn(file);
        Mockito.when(file.getFilename()).thenReturn("filename");
        ResponseEntity<?> response = fileController.serveFile("filename");

        assertEquals(ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"filename\"").body(file), response);
        Mockito.verify(mockStorageService).loadAsResource("filename");
        Mockito.verify(file).getFilename();
    }
}