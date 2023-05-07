package arctic.example.pi.service;
import arctic.example.pi.entity.Evenement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import arctic.example.pi.entity.Sponsor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Set;

public class ExcelExportSponsors {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Sponsor> sponsorsList;

    public ExcelExportSponsors(List<Sponsor> sponsorsList) {
        this.sponsorsList = sponsorsList;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if (value instanceof Double){
            cell.setCellValue((Double) value);
        }else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long){
            cell.setCellValue((Long) value);
        }else if (value instanceof Date){
            cell.setCellValue((Date) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createHeaderRow(){
        sheet   = workbook.createSheet("Sponsors Information");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();



        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "Sponsors Information", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);
        createCell(row, 0, "Sponsor Name", style);
        createCell(row, 1, "Description", style);
        createCell(row, 2, "Contract start date", style);
        createCell(row, 3, "Contract end date", style);
        //createCell(row, 4, "Event Name", style);

    }

    private void writeCustomerData(){
        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Sponsor sponsor : sponsorsList){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, sponsor.getNomSponsor(), style);
            createCell(row, columnCount++, sponsor.getDescription(), style);
            createCell(row, columnCount++, sponsor.getDebutContract(), dateCellStyle);
            createCell(row, columnCount++, sponsor.getFinContract(), dateCellStyle);

            /*Set<Evenement> events = sponsor.getEvent();
            StringBuilder eventsNames = new StringBuilder();
            for (Evenement event : events) {
                eventsNames.append(event.getNomEvent()).append(", ");
            }
            if (eventsNames.length() > 0) {
                eventsNames.delete(eventsNames.length() - 2, eventsNames.length());
            } else {
                eventsNames.append("None");
            }
            createCell(row, columnCount++, eventsNames.toString(), style);*/

        }

    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeCustomerData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
