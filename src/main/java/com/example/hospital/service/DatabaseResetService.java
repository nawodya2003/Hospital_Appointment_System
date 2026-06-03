package com.example.hospital.service;

/**
 * Service for resetting the application database.
 */
public interface DatabaseResetService {

    /**
     * Reset all application tables and restart auto-generated IDs at 1.
     */
    void resetDatabase();
}
