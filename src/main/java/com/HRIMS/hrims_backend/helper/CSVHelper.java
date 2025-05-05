package com.HRIMS.hrims_backend.helper;

import com.HRIMS.hrims_backend.entity.Department;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


import java.io.ByteArrayInputStream;
import org.apache.commons.csv.QuoteMode;


public class CSVHelper {

    public static ByteArrayInputStream departmentsToCSV(List<Department> departments) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Department department : departments) {
                List<String> data = Arrays.asList(
                        String.valueOf(department.getId()),
                        department.getName(),
                        department.getCode()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
