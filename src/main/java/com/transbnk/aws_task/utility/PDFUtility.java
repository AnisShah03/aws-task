package com.transbnk.aws_task.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Component;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.transbnk.aws_task.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PDFUtility {

    public ByteArrayInputStream generatePdf(List<User> usersList) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream)) {// Links the PDF content to the outputStream.
                                                                          // It enables writing to the byte array.
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);// Represents the internal structure of the PDF. It
                                                                 // works with PdfWriter to write PDF content to
                                                                 // outputStream.
            Document document = new Document(pdfDocument);// Document is used to design and add layout components

            document.add(new Paragraph("Users List").setBold().setFontSize(16));

            float[] columnWidth = { 50F, 150F, 150F, 150F };

            // creating table
            Table table = new Table(columnWidth);
            table.addCell("ID");
            table.addCell("Username");
            table.addCell("password");
            table.addCell("phone_number");

            usersList.forEach(user -> {
                table.addCell(String.valueOf(user.getId()));
                table.addCell(user.getUsername());
                table.addCell(user.getPassword());
                table.addCell(String.valueOf(user.getPhone_num()));
            });

            // attaching table to the document
            document.add(table);
            document.close();

            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ByteArrayInputStream generateHtmlGToPdf(List<User> usersList) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            FileInputStream htmlTemplateStream = new FileInputStream(
                    "src\\main\\resources\\templates\\pdf_template.html");
            String htmlTemplate = new String(htmlTemplateStream.readAllBytes(), StandardCharsets.UTF_8);

            StringBuilder rows = new StringBuilder();

            usersList.forEach(user -> {
                rows.append("<tr>")
                        .append("<td>").append(user.getId()).append("</td>")
                        .append("<td>").append(user.getUsername()).append("</td>")
                        .append("<td>").append(user.getPassword()).append("</td>")
                        .append("<td>").append(user.getPhone_num()).append("</td>")
                        .append("</tr>");
            });

            String updatedTemplate = htmlTemplate.replace("{{rows}}", rows.toString());
            HtmlConverter.convertToPdf(updatedTemplate, byteArrayOutputStream);
            htmlTemplateStream.close();

            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            log.error("Exception during PDF generation: {}", e.getMessage());
            return null;
        }

    }

}
