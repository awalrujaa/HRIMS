package com.HRIMS.hrims_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String status;
    private String message;
    private LocalDateTime timeStamp;
    private T data;
    private List<String> errors = new ArrayList<>(); // Collections.emptyList(); //
}

