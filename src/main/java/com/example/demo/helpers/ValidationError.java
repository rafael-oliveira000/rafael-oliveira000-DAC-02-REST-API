package com.example.demo.helpers;
import org.springframework.http.HttpStatus;

public class ValidationError {
    private HttpStatus status = HttpStatus.OK;
    private String message;

    public ValidationError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ValidationError() { }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Boolean hasError() {
        return !this.status.is2xxSuccessful() && !this.message.isEmpty();
    }
}
