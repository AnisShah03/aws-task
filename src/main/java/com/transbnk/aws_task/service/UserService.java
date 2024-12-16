package com.transbnk.aws_task.service;

import java.io.InputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseEntity<?> uploadExcel(InputStream inputStream);

    ResponseEntity<?> downloadDB();

    ResponseEntity<?> generateHtmlToPdf();

    ResponseEntity<?> uploadFile(MultipartFile multipartFile);

    ResponseEntity<?> downloadFile(String key);
}
