package com.dtt.services.impl;

import com.dtt.pojo.Student;
import com.dtt.pojo.User;
import com.dtt.services.ExportService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExportServiceImpl implements ExportService {

    @Override
    public void exportCSV(List<Student> students, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=student_report.csv");

        OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        osw.write('\uFEFF'); // BOM cho UTF-8

        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("Họ và tên\tEmail\tKhoa\tLớp\tĐiểm 1\tĐiểm 2\tĐiểm 3\tĐiểm 4\tTổng điểm\tXếp loại\n");

        // Dữ liệu
        for (Student s : students) {
            sb.append(s.getUser().getName()).append("\t")
                    .append(s.getUser().getEmail()).append("\t")
                    .append(s.getFaculty().getName()).append("\t")
                    .append(s.getClassRoom().getName()).append("\t")
                    .append(s.getUser().getPoint_1()).append("\t")
                    .append(s.getUser().getPoint_2()).append("\t")
                    .append(s.getUser().getPoint_3()).append("\t")
                    .append(s.getUser().getPoint_4()).append("\t")
                    .append(s.getTotalPoints()).append("\t")
                    .append(s.getClassify()).append("\n");
        }

        osw.write(sb.toString());
        osw.flush();
        osw.close();
    }

    @Override
    public void exportPDF(List<Student> students, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=student_report.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        BaseFont baseFont = BaseFont.createFont(
                "C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(baseFont, 13, Font.BOLD);
        Font headerFont = new Font(baseFont, 12, Font.BOLD);
        Font bodyFont = new Font(baseFont, 12);

        Paragraph title = new Paragraph("Báo cáo điểm rèn luyện sinh viên", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(10); // 10 cột
        table.setWidthPercentage(100);
        table.setWidths(new int[]{4, 5, 3, 3, 2, 2, 2, 2, 3, 3});

        String[] headers = {
            "Họ và tên", "Email", "Khoa", "Lớp",
            "Điểm 1", "Điểm 2", "Điểm 3", "Điểm 4",
            "Tổng điểm", "Xếp loại"
        };

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        for (Student s : students) {
            addCell(table, s.getUser().getName(), bodyFont);
            addCell(table, s.getUser().getEmail(), bodyFont);
            addCell(table, s.getFaculty().getName(), bodyFont);
            addCell(table, s.getClassRoom().getName(), bodyFont);
            addCell(table, String.valueOf(s.getUser().getPoint_1()), bodyFont);
            addCell(table, String.valueOf(s.getUser().getPoint_2()), bodyFont);
            addCell(table, String.valueOf(s.getUser().getPoint_3()), bodyFont);
            addCell(table, String.valueOf(s.getUser().getPoint_4()), bodyFont);
            addCell(table, String.valueOf(s.getTotalPoints()), bodyFont);
            addCell(table, s.getClassify(), bodyFont);
        }

        document.add(table);
        document.close();
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        table.addCell(cell);
    }
}
