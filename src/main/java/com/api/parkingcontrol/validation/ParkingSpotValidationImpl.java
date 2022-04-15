package com.api.parkingcontrol.validation;


import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.NoSuchElementException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class ParkingSpotValidationImpl  {

    final  ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotValidationImpl(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }





    public ParkingSpotDto validationLicensePLate(ParkingSpotDto parkingSpotDto) throws DataIntegrityViolationException {

        if (existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber()) ){
            throw new DataIntegrityViolationException("Conflito in Spot Number");
        }else if (existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar() )) {
            throw new DataIntegrityViolationException("Conflito in License Plate car");
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

