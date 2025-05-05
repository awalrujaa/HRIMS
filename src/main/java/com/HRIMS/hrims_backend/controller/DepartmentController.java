package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.DepartmentRequest;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.helper.ExcelHelper;
import com.HRIMS.hrims_backend.service.CSVService;
import com.HRIMS.hrims_backend.service.DepartmentService;
import com.HRIMS.hrims_backend.service.ExcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin()
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ExcelService excelService;
    CSVService fileService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, ExcelService excelService, CSVService fileService) {
        this.departmentService = departmentService;
        this.excelService = excelService;
        this.fileService = fileService;
    }

    @PostMapping
    public ApiResponse<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest, Principal principal){
        log.info("current user: {}", principal.getName());
        return departmentService.createDepartment(departmentRequest);
    }

    @PostMapping("/bulk")
    public ApiResponse<List<DepartmentResponse>> createDepartment(@Valid @RequestBody List<DepartmentRequest> departmentRequest){
        return departmentService.bulkUploadDepartments(departmentRequest);
    }

    @GetMapping
    public ApiResponse<PaginatedResponse<DepartmentResponse>> getDepartments(@RequestParam(defaultValue = "0") int pageNum,
                                                                             @RequestParam(defaultValue = "10") int pageSize){
            return departmentService.getAllDepartments(pageNum, pageSize);
    }

    @GetMapping("/{departmentId}")
    public ApiResponse<DepartmentResponse> getDepartmentById(@PathVariable Long departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @PutMapping("/{departmentId}")
    public ApiResponse<DepartmentResponse> updateDepartment(@PathVariable Long departmentId,
                                                            @RequestBody @Valid DepartmentRequest departmentRequest){
        return departmentService.updateDepartment(departmentId, departmentRequest);
    }

    @DeleteMapping("/{departmentId}")
    public ApiResponse<String> deleteDepartmentById(@PathVariable Long departmentId){
        return departmentService.deleteDepartmentById(departmentId);
    }

    @GetMapping("/filter")
    public ApiResponse<PaginatedResponse<DepartmentResponse>> filterDepartmentsPagination(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String updatedBy,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        return departmentService.filterDepartmentsPagination(name, code, createdBy, updatedBy, pageNum, pageSize);
    }

    @PostMapping("/excel-upload")
    public ApiResponse<String> uploadFile(@RequestParam("file")MultipartFile file) {
        return departmentService.uploadFile(file);
    }

    @GetMapping("/download-csv")
    public ResponseEntity<Resource> getCSVFile() {
        String filename = "departments.csv";
        InputStreamResource file = new InputStreamResource(fileService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/download-excel")
    public ResponseEntity<Resource> getExcelFile() {
        String filename = "departments.xlsx";
        InputStreamResource file = new InputStreamResource(excelService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

}
