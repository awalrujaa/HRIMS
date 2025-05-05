package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.helper.ExcelHelper;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final DepartmentRepository departmentRepository;

    public void save(MultipartFile file) {
        try {
            List<Department> departments = ExcelHelper.excelToDepartments(file.getInputStream());
            departmentRepository.saveAll(departments);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


    public ByteArrayInputStream load() {
        List<Department> departments = departmentRepository.findAll();

        ByteArrayInputStream in = ExcelHelper.departmentsToExcel(departments);
        return in;
    }
}
