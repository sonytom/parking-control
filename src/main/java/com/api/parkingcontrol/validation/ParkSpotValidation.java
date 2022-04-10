package com.api.parkingcontrol.validation;


import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class ParkSpotValidation implements ParkingSpotValidationInterface {

    final  ParkingSpotRepository parkingSpotRepository;



    @Override
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

   public Optional<ParkingSpotModel> ifParkingSpotNotempty (Optional<ParkingSpotModel> parkingSpotModelOptional) throws ResourceNotFoundException {
       if (parkingSpotModelOptional.isEmpty()) {
           throw new ResourceNotFoundException("não tem ");
       } else {
           return parkingSpotModelOptional;
       }
   }


    public ParkSpotValidation(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) throws DataIntegrityViolationException {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }
    private boolean existsByParkingSpotNumber(String parkingSpotNumber) throws DataIntegrityViolationException {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }
    private boolean existsByApartmentAndBlock(String apartment, String block) throws DataIntegrityViolationException {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment,block);
    }

    public ParkingSpotModel notAllowedMod(UUID parkingID, ParkingSpotModel parkingSpotDetails) throws ResourceNotFoundException {

        ParkingSpotModel parkingSpotModel = parkingSpotRepository.findById(parkingID)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found:: " + parkingID));
        if (!(parkingSpotModel.getId().equals(parkingSpotDetails.getId())) || ((parkingSpotModel.getRegistrationDate().equals(parkingSpotDetails.getRegistrationDate())))){

            System.out.println("oi");

        }


        return parkingSpotDetails;
    }
}

