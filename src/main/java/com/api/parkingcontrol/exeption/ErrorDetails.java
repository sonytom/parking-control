package com.api.parkingcontrol.exeption;


import lombok.Data;


import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;


    public ErrorDetails(String message, String details) {
        this.message = message;
        this.details = details;
    }
}
