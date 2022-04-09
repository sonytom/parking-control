package com.api.parkingcontrol.exeption;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataIntegrityViolationException extends Exception{

    public DataIntegrityViolationException(String message){
        super(message);
    }


}
