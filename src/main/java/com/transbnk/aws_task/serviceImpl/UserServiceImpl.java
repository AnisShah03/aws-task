package com.transbnk.aws_task.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.transbnk.aws_task.model.User;
import com.transbnk.aws_task.repository.UserRepository;
import com.transbnk.aws_task.service.UserService;
import com.transbnk.aws_task.utility.ExcelUtility;
import com.transbnk.aws_task.utility.PDFUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final ExcelUtility excelUtility;
    private final PDFUtility pdfUtility;

    private final UserRepository userRepository;

    private final AmazonS3 amazonS3;

    @Value("${spring.aws.bucketName}")
    private String bucketName;

    @Override
    public ResponseEntity<?> uploadExcel(InputStream inputStream) {
        List<User> userList = excelUtility.excelUpload(inputStream);
        userRepository.saveAll(userList);
        return ResponseEntity.ok().body("excel file uploaded");
    }

    @Override
    public ResponseEntity<?> downloadDB() {

        List<User> users = userRepository.findAll();
        ByteArrayInputStream pdf = pdfUtility.generatePdf(users);

        Resource inputStreamResource = new InputStreamResource(pdf);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=usersList.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(inputStreamResource);

    }

    @Override
    public ResponseEntity<?> generateHtmlToPdf() {

        List<User> usersList = userRepository.findAll();

        ByteArrayInputStream htmlToPdf = pdfUtility.generateHtmlGToPdf(usersList);
        Resource inputStreamResource = new InputStreamResource(htmlToPdf);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=htmlPdfUser.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(inputStreamResource);
    }

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile multipartFile) {

        Optional<File> file = convertToFile(multipartFile);

        // creating file name for uploading
        String file_name = System.currentTimeMillis() + multipartFile.getOriginalFilename();

        file.ifPresentOrElse(f -> {
            amazonS3.putObject(bucketName, file_name, f);
            f.delete();
        }, () -> new NullPointerException("Conversion Failed..."));

        return ResponseEntity.ok("File uploaded sucessfully...");
        // return ResponseEntity.badRequest().body("File not uploaded...");

    }

    private Optional<File> convertToFile(MultipartFile multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            File file = new File(multipartFile.getOriginalFilename());// created file with file-name
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file); // here we have used fos to write the
                                                                                // content into file object
                fileOutputStream.write(multipartFile.getBytes()); // with this help of fos writing the content to th fil
                                                                  // object
                fileOutputStream.close();
                return Optional.of(file);
            } catch (IOException e) {
                log.error("IOException: {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public ResponseEntity<?> downloadFile(String key) {
        if (key != null) {
            S3Object object = amazonS3.getObject(bucketName, key);
            InputStream objectContent = object.getObjectContent();

            Resource inputStreamResource = new InputStreamResource(objectContent);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + key)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inputStreamResource);
        }
        return ResponseEntity.internalServerError().body("something went wrong...");
    }

}
