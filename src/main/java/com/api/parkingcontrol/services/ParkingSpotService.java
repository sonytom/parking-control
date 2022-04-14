package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.NoSuchElementException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface ParkingSpotService {
    /**
     * IF ok saved on bd
     *
     * @return Return ParkingSpotModel
     *
     * @throws ResourceNotFoundException if dados if comflict throw exeption
     *
     */
    ParkingSpotModel save (ParkingSpotDto parkingSpotDto) throws DataIntegrityViolationException, ResourceNotFoundException;

    ParkingSpotModel getParkingSpotById(UUID parkingID) throws ResourceNotFoundException, NoSuchElementException;

    List<ParkingSpotModel> getAll() throws ResourceNotFoundException, NoSuchElementException;

    Map<String, Boolean> deleteParkingSpot(UUID parkingID) throws ResourceNotFoundException;

    ParkingSpotModel updateParkingSpot(UUID parkingID, @Valid ParkingSpotDto parkingSpotDetails) throws ResourceNotFoundException;

}
