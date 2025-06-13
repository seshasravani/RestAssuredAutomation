package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class ExcelReader {
    private String path;
    private Workbook workbook;

    public ExcelReader(String path) {
        this.path = path;
        try {
            FileInputStream fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> getData(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(0);
        int totalRows = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < totalRows; i++) {
            Row currentRow = sheet.getRow(i);
            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                Cell headerCell = headerRow.getCell(j);
                Cell currentCell = currentRow.getCell(j);
                String header = headerCell.getStringCellValue();
                String value = "";
                if(currentCell != null) {
                    currentCell.setCellType(CellType.STRING);
                    value = currentCell.getStringCellValue();
                }
                rowData.put(header, value);
            }
            data.add(rowData);
        }
        return data;
    }
}
