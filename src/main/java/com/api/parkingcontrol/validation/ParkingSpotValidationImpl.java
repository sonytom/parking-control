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


    public Optional<ParkingSpotModel> ifParkingSpotNotempty (ParkingSpotModel parkingSpotModelOptional) throws ResourceNotFoundException, NoSuchElementException {
        Optional<ParkingSpotModel> parkingSpotValid = Optional.ofNullable(parkingSpotModelOptional);

        if (parkingSpotValid.isEmpty()) {

            throw new ResourceNotFoundException("não tem ");

        } else if (!existsById(parkingSpotModelOptional.getId())) {

            throw new NoSuchElementException("Não existe no banco de dados ");

        } else {
            return parkingSpotValid;
        }
    }




  // return parkingSpotRepository.existsById(id);



    public ParkingSpotModel notAllowedMod(UUID parkingID, ParkingSpotModel parkingSpotDetails) throws ResourceNotFoundException {

        ParkingSpotModel parkingSpotModel = parkingSpotRepository.findById(parkingID)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found:: " + parkingID));
        if (!(parkingSpotModel.getId().equals(parkingSpotDetails.getId())) || ((parkingSpotModel.getRegistrationDate().equals(parkingSpotDetails.getRegistrationDate())))){
            System.out.println("oi");
        }

        return parkingSpotDetails;

    }



    public boolean existsById (UUID id)  {
        return parkingSpotRepository.existsById(id);
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

