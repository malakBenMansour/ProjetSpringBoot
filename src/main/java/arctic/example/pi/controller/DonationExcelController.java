package arctic.example.pi.controller;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import arctic.example.pi.entity.Donations;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class DonationExcelController {
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static List<Donations> donationsList;

    public DonationExcelController(List<Donations> listUsers) {
        this.donationsList = listUsers;
        workbook = new XSSFWorkbook();
    }


    private static void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "description", style);
        createCell(row, 1, "Addresse", style);
        createCell(row, 2, "nom Association", style);
        // createCell(row, 3, "Roles", style);
        createCell(row, 3, "Telephone", style);

    }

    private static void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private static void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Donations user : donationsList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getDescription(), style);
            createCell(row, columnCount++, user.getAssociations().getAdresse(), style);
            createCell(row, columnCount++, user.getAssociations().getNom(), style);
            createCell(row, columnCount++, user.getAssociations().getTel(), style);
            //  createCell(row, columnCount++, user.isEnabled(), style);

        }
    }

    public void   export(String filePath) throws IOException {
        writeHeaderLine();
        writeDataLines();

        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
