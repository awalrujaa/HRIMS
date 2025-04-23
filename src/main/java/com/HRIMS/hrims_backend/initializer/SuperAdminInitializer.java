package com.HRIMS.hrims_backend.initializer;

import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.enums.RoleType;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public SuperAdminInitializer(EmployeeRepository employeeRepository,
                                 DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }


    @Override
    public void run(String... args) {
        String defaultUsername = "superadmin";
        String defaultEmail = "superadmin@hrims.com";
        String defaultDeptName = "Administration";
        String defaultDeptCode = "ADMIN001";

        // Step 1: Create department if it doesn't exist
        Department department = departmentRepository.findByCode(defaultDeptCode)
                .orElseGet(() -> {
                    Department dept = new Department();
                    dept.setName(defaultDeptName);
                    dept.setCode(defaultDeptCode);
                    return departmentRepository.save(dept);
                });

        // Step 2: Create Super Admin if not already present
        if (employeeRepository.findByUserName(defaultUsername).isEmpty()) {
            Employee superAdmin = new Employee();
            superAdmin.setEmail(defaultEmail);
            superAdmin.setUserName(defaultUsername);
            superAdmin.setPassword(PasswordUtil.hashPassword("SuperSecurePass123"));
            superAdmin.setRole(RoleType.SUPER_ADMIN.toString());
            superAdmin.setDepartmentId(department.getId());

            employeeRepository.save(superAdmin);

            System.out.println("Super Admin initialized with department: " + department.getName());
        } else {
            System.out.println("Super Admin already exists.");
        }
    }
}
