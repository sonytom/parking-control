package com.api.parkingcontrol.validation;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public interface ParkingSpotValidationInterface {
    ParkingSpotDto validationLicensePLate(ParkingSpotDto parkingSpotDto) throws DataIntegrityViolationException;

}
