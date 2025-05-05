package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.helper.CSVHelper;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class CSVService {

    @Autowired
    DepartmentRepository departmentRepository;

    public ByteArrayInputStream load() {
        List<Department> departmentList = departmentRepository.findAll();

        ByteArrayInputStream in = CSVHelper.departmentsToCSV(departmentList);
        return in;
    }
}
