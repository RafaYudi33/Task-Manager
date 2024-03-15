package br.com.rafaelyudi.todoList.Errors;

import java.time.LocalDateTime;
import java.util.Date;

public class CustomResponseError {

    private String message;
    private LocalDateTime timestamp;

    private String details;

    public CustomResponseError(String message, LocalDateTime timestamp, String details) {
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
