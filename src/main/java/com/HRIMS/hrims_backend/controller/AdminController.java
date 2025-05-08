package com.HRIMS.hrims_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super-admin")
public class AdminController {

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    String superAdminPage() {
        return "Super Admin";
    }
}
