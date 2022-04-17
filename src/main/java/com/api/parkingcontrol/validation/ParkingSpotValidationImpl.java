package com.api.parkingcontrol.validation;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ParkingSpotValidationImpl  {
    final  ParkingSpotRepository parkingSpotRepository;
    public ParkingSpotDto validationDataConflict(ParkingSpotDto parkingSpotDto) throws DataIntegrityViolationException {

        if (existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber()) ){
            throw new DataIntegrityViolationException("conflict: in Spot Number is alredy in uses");
        }else if (existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar() )) {
            throw new DataIntegrityViolationException("conflict: in License Plate car is alredy in uses");
        }else if (existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            throw new DataIntegrityViolationException("conflict: parking spot is alredy in uses");
        }else{
            return parkingSpotDto;
        }

    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }
    private boolean existsByParkingSpotNumber(String parkingSpotNumber)  {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }
    private boolean existsByApartmentAndBlock(String apartment, String block)  {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment,block);
    }
}