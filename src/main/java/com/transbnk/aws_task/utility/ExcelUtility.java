package com.transbnk.aws_task.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.transbnk.aws_task.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExcelUtility {

    public boolean isExcel(MultipartFile file) {
        return Objects.requireNonNull(
                file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

    public List<User> excelUpload(InputStream inputStream) {

        List<User> users = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheet("user");

            for (Row row : sheet) {

                if (row.getRowNum() == 0)
                    continue;

                // Integer id = (int) row.getCell(0).getNumericCellValue();
                String username = row.getCell(1).getStringCellValue();
                String password = row.getCell(2).getStringCellValue();
                Long phone_num = (long) row.getCell(3).getNumericCellValue();

                User user = User.builder()
                        // .id(id)
                        .username(username)
                        .password(password)
                        .phone_num(phone_num)
                        .build();
                users.add(user);
            }

            return users;
        } catch (IOException e) {
            log.info("IOException: ", e.getMessage());
            return null;
        }

    }

}
