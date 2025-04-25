package com.HRIMS.hrims_backend.helper;

import com.HRIMS.hrims_backend.entity.Department;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Id", "Name", "Code"};
    static String SHEET = "Departments";

    public static boolean hasExcelFormet(MultipartFile file) {

        if(!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Department> excelToDepartments(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                System.out.println("Sheet " + i + ": " + workbook.getSheetName(i));
            }

            Sheet sheet = workbook.getSheet(SHEET);
            if (sheet == null) {
                sheet = workbook.getSheetAt(0); // get the first sheet as fallback
            }

//            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Department> departments = new ArrayList<Department>();

            int rowNumber = 0;

            while(rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Department department = new Department();

                int cellIdx = 0;

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            department.setName(currentCell.getStringCellValue());
                            break;

                        case 1:
                            String cellType = currentCell.getCellType().toString();
                            if (Objects.equals(cellType, "NUMERIC")) {
                                department.setCode(String.valueOf((long) currentCell.getNumericCellValue()));
                                break;
                            }
                            department.setCode(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                departments.add(department);
            }

            workbook.close();

            return departments;

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
