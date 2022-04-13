package com.api.parkingcontrol.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoSuchElementException extends Exception{


    public NoSuchElementException(String message){
        super(message);
    }

}
