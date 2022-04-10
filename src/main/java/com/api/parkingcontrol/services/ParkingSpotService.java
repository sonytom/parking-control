package com.api.parkingcontrol.services;

import com.api.parkingcontrol.controllers.ParkingSpotController;
import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.validation.ParkSpotValidation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ParkingSpotService implements ParkingSpotServiceInterface {

    final ParkingSpotRepository parkingSpotRepository;
    final ParkSpotValidation parkSpotValidation;



    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository, ParkSpotValidation parkSpotValidation) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.parkSpotValidation = parkSpotValidation;
    }

    @Transactional
    @Override
    public ResponseEntity<ParkingSpotModel> save(ParkingSpotDto parkingSpotDto) throws DataIntegrityViolationException{
        ParkingSpotDto validation = parkSpotValidation.validationLicensePLate(parkingSpotDto);
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(validation, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        parkingSpotRepository.save(parkingSpotModel);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModel);
    }

    public ResponseEntity<ParkingSpotModel> getParkingSpotById(UUID parkingID)  throws ResourceNotFoundException {
        Optional<ParkingSpotModel> parkingSpotID = parkingSpotRepository.findById(parkingID);
                 parkSpotValidation.ifParkingSpotNotempty(parkingSpotID);
        // validation empty ^
        parkingSpotID.get().add(linkTo(methodOn(ParkingSpotController.class).getAllParkingSpots()).withRel("List all ParkSpots"));
        return new ResponseEntity<>(parkingSpotID.get(), HttpStatus.OK);
    }


    public ResponseEntity<List<ParkingSpotModel>> getAll() throws ResourceNotFoundException {
        List<ParkingSpotModel> getAllparking = parkingSpotRepository.findAll();
        for (ParkingSpotModel parkingSpotModel : getAllparking) {
            UUID parkingID = parkingSpotModel.getId();
            parkingSpotModel.add(linkTo(methodOn(ParkingSpotController.class).getParkingSpotById(parkingID)).withSelfRel());
        }
        return new ResponseEntity<>(getAllparking, HttpStatus.OK);
        }

   public Map<String, Boolean> deleteParkingSpot(UUID parkingID) throws ResourceNotFoundException {
       ParkingSpotModel parkingSpotModel = parkingSpotRepository.findById(parkingID)
               .orElseThrow(() -> new ResourceNotFoundException("No heve for delete :: " + parkingID));
       parkingSpotRepository.delete(parkingSpotModel);
       Map<String, Boolean> response = new HashMap<>();
       response.put("deleted", Boolean.TRUE);
       return response;
   }

    public ParkingSpotModel updateParkingSpot(UUID parkingID, @Valid ParkingSpotDto parkingSpotDetails) throws ResourceNotFoundException {

        // validation noot alowed change date and id
        // if and exists conflict

        ParkingSpotModel parkingSpotModel = parkingSpotRepository.findById(parkingID)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found:: " + parkingID));
        BeanUtils.copyProperties(parkingSpotDetails, parkingSpotModel);



        parkingSpotModel.setParkingSpotNumber(parkingSpotDetails.getParkingSpotNumber());
        parkingSpotModel.setLicensePlateCar(parkingSpotDetails.getLicensePlateCar());
        parkingSpotModel.setBrandCar(parkingSpotDetails.getBrandCar());
        parkingSpotModel.setModelCar(parkingSpotDetails.getModelCar());
        parkingSpotModel.setColorCar(parkingSpotDetails.getColorCar());
        parkingSpotModel.setResponsibleName(parkingSpotDetails.getResponsibleName());
        parkingSpotModel.setApartment(parkingSpotDetails.getApartment());
        parkingSpotModel.setBlock(parkingSpotDetails.getBlock());

            return parkingSpotRepository.save(parkingSpotModel);



    }
}


