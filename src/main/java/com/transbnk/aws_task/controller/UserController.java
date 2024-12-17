package com.transbnk.aws_task.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transbnk.aws_task.service.UserService;
import com.transbnk.aws_task.utility.ExcelUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ExcelUtility excelUtility;

    @PostMapping("/excel-upload")
    public ResponseEntity<?> excelupload(@RequestParam(name = "file") MultipartFile multipartFile) {
        if (excelUtility.isExcel(multipartFile)) {
            try {
                return userService.uploadExcel(multipartFile.getInputStream());

            } catch (IOException e) {
                log.info("IOException: ", e.getMessage());
                return ResponseEntity.badRequest().body("something went wrong...");
            }
        }
        return ResponseEntity.badRequest().body("please provide valid excel file");
    }

    @GetMapping("/db/download-pdf")
    public ResponseEntity<?> downloadPdfFromDB() {
        return userService.downloadDB();
    }

    @GetMapping("/db/html-pdf")
    public ResponseEntity<?> generateHtmlPdfFromDB() {
        return userService.generateHtmlToPdf();
    }

    @PostMapping("/file/upload")
    public ResponseEntity<?> uploadingFile(@RequestParam(name = "file") MultipartFile multipartFile) {
        if (multipartFile != null && multipartFile.getContentType() != null
                && multipartFile.getContentType().equalsIgnoreCase("application/pdf")) {
            return userService.uploadFile(multipartFile);
        }
        return ResponseEntity.badRequest().body("Only upload pdf file...");
    }

    @GetMapping("/file/download")
    public ResponseEntity<?> getMethodName(@RequestParam String key) {
        return userService.downloadFile(key);
    }

}
