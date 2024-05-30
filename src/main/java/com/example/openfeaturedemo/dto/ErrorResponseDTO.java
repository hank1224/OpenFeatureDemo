package com.example.openfeaturedemo.dto;

public class ErrorResponseDTO {
    private String error;
    private String message;
    private int status;

    public ErrorResponseDTO(String error, String message, int status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    // Getters and setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
