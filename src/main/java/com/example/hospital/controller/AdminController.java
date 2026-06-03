package com.example.hospital.controller;

import com.example.hospital.payload.ApiResponse;
import com.example.hospital.service.DatabaseResetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin controller for maintenance actions.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final DatabaseResetService databaseResetService;

    public AdminController(DatabaseResetService databaseResetService) {
        this.databaseResetService = databaseResetService;
    }

    /**
     * Reset the database and restart auto-generated IDs at 1.
     * POST /api/admin/reset
     */
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse<Void>> resetDatabase() {
        databaseResetService.resetDatabase();
        return ResponseEntity.ok(
                ApiResponse.success("Database reset successfully", null, HttpStatus.OK.value()));
    }
}
