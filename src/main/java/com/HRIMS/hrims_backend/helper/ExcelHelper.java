package com.HRIMS.hrims_backend.helper;

import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.entity.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    public static List<Employee> excelToEmployees(InputStream is) {
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

            List<Employee> employees = new ArrayList<Employee>();

            int rowNumber = 0;

            while(rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Employee employee = new Employee();

                int cellIdx = 0;

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            employee.setFirstName(currentCell.getStringCellValue());
                            break;

                        case 1:
                            employee.setMiddleName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            employee.setLastName(currentCell.getStringCellValue());
                            break;

//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 0:
//                            employee.setFirstName(currentCell.getStringCellValue());
//                            break;
//
//                        case 1:
//                            String cellType = currentCell.getCellType().toString();
//                            if (Objects.equals(cellType, "NUMERIC")) {
//                                department.setCode(String.valueOf((long) currentCell.getNumericCellValue()));
//                                break;
//                            }
//                            department.setCode(currentCell.getStringCellValue());
//                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                employees.add(employee);
            }

            workbook.close();

            return employees;

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream departmentsToExcel(List<Department> departments) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Department department : departments) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(department.getId());
                row.createCell(1).setCellValue(department.getName());
                row.createCell(2).setCellValue(department.getCode());
                row.createCell(3).setCellValue(department.getCreatedBy());
                row.createCell(4).setCellValue(department.getCreatedAt());
                row.createCell(5).setCellValue(department.getUpdatedBy());
                row.createCell(6).setCellValue(department.getUpdatedAt());

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

}
