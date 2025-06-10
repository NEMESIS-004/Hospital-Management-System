package com.project.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.model.Notification;

@Service
public class PdfService {

	public byte[] generateNotificationPDF(Notification notification) throws DocumentException, IOException {
		Document document = new Document();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        document.addTitle("Notification");
        document.add(new Paragraph("Notification ID: " + notification.getNotificationId()));
        document.add(new Paragraph("User: " + notification.getUser().getName()));
        document.add(new Paragraph("Message: " + notification.getMessage()));
        document.add(new Paragraph("Status: " + notification.getStatus()));
        document.close();
       
        return byteArrayOutputStream.toByteArray();

}

}
