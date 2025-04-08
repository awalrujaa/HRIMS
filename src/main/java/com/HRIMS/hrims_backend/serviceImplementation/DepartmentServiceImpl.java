package com.HRIMS.hrims_backend.serviceImplementation;

import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    public DepartmentServiceImpl (DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        // If the department is not found, throw an exception
        if (department.isEmpty()) {
            throw new RuntimeException("Department not found with id " + id);
        }
        return department;
    }

    @Override
    public Department updateDepartment(Long id, Department departmentDetails) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        // If the department is not found, throw an exception (or handle it as needed)
        if (optionalDepartment.isEmpty()) {
            throw new RuntimeException("Department not found with id " + id);
        }

        // Get the existing department from the optional object
        Department department = optionalDepartment.get();
        department.setDepartmentName(departmentDetails.getDepartmentName());
        department.setDepartmentCode(departmentDetails.getDepartmentCode());
        return departmentRepository.save(department);
    }

    @Override
    public String deleteDepartmentById(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        // If the department is not found, throw an exception (or handle it as needed)
        if (optionalDepartment.isEmpty()) {
            return "Department not found with id " + id;
        }
        departmentRepository.deleteById(id);
        return "Success";
    }

    @Override
    public String deleteDepartments() {
        departmentRepository.deleteAll();
        return "Deleted All Departments";
    }

}
