package com.dtt.services.impl;

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
    public void exportCSV(List<User> users, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=student_report.csv");

        OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");

        osw.write('\uFEFF'); // BOM để Excel nhận UTF-8

        // Tự ghi bằng tab thay vì CSVWriter (vì CSVWriter dùng dấu phẩy)
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("Họ và tên\tEmail\tĐiểm 1\tĐiểm 2\tĐiểm 3\tĐiểm 4\tTổng điểm\tXếp loại\n");

        // Dữ liệu
        for (User user : users) {
            sb.append(user.getName()).append("\t")
                    .append(user.getEmail()).append("\t")
                    .append(user.getPoint_1()).append("\t")
                    .append(user.getPoint_2()).append("\t")
                    .append(user.getPoint_3()).append("\t")
                    .append(user.getPoint_4()).append("\t")
                    .append(user.totalPoint()).append("\t")
                    .append(user.classify(user.totalPoint())).append("\n");
        }

        osw.write(sb.toString());
        osw.flush();
        osw.close();
    }

    @Override
    public void exportPDF(List<User> users, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=student_report.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Use a font that supports Vietnamese characters
        BaseFont baseFont = BaseFont.createFont(
                "C:\\Windows\\Fonts\\times.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );
        Font titleFont = new Font(baseFont, 16, Font.BOLD);
        Font headerFont = new Font(baseFont, 12, Font.BOLD);
        Font bodyFont = new Font(baseFont, 12);

        // Add report title
        Paragraph title = new Paragraph("Báo cáo điểm rèn luyện sinh viên", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // Empty line

        // Create PDF table
        PdfPTable table = new PdfPTable(8); // 8 columns
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 4, 2, 2, 2, 2, 2, 3});

        // Headers
        String[] headers = {"Họ và tên", "Email", "Điểm 1", "Điểm 2", "Điểm 3", "Điểm 4", "Tổng điểm", "Xếp loại"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Body
        for (User user : users) {
            addCell(table, user.getName(), bodyFont);
            addCell(table, user.getEmail(), bodyFont);
            addCell(table, String.valueOf(user.getPoint_1()), bodyFont);
            addCell(table, String.valueOf(user.getPoint_2()), bodyFont);
            addCell(table, String.valueOf(user.getPoint_3()), bodyFont);
            addCell(table, String.valueOf(user.getPoint_4()), bodyFont);
            addCell(table, String.valueOf(user.totalPoint()), bodyFont);
            addCell(table, user.classify(user.totalPoint()), bodyFont);
        }

        document.add(table);
        document.close();
    }

// Helper method to add cells with proper formatting
    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        table.addCell(cell);
    }

}
