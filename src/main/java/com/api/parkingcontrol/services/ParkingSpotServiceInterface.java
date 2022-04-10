package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.http.ResponseEntity;

public interface ParkingSpotServiceInterface {
    ResponseEntity<ParkingSpotModel> save (ParkingSpotDto parkingSpotDto) throws  DataIntegrityViolationException;

}
