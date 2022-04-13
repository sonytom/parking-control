package com.api.parkingcontrol.validation;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.NoSuchElementException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface ParkingSpotValidation {
    ParkingSpotDto validationLicensePLate(ParkingSpotDto parkingSpotDto) throws DataIntegrityViolationException;
    Optional<ParkingSpotModel> ifParkingSpotNotempty (ParkingSpotModel parkingSpotModelOptional) throws ResourceNotFoundException, NoSuchElementException;
    ParkingSpotModel notAllowedMod(UUID parkingID, ParkingSpotModel parkingSpotDetails) throws ResourceNotFoundException;
}
