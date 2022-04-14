package com.api.parkingcontrol.services;

import com.api.parkingcontrol.controllers.ParkingSpotController;
import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.NoSuchElementException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    final ParkingSpotRepository parkingSpotRepository;


    public ParkingSpotServiceImpl(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;

    }

    @Transactional
    @Override
    public ParkingSpotModel save(ParkingSpotDto parkingSpotDto) {
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return Optional.of(parkingSpotRepository.save(parkingSpotModel)).orElseThrow(() -> new ResourceNotFoundException("lista vazia"));
    }



    public ParkingSpotModel getParkingSpotById(UUID parkingID)  {
                      Optional<ParkingSpotModel> parkingSpotID = Optional.ofNullable(parkingSpotRepository.findById(parkingID).orElseThrow(() ->
                      new ResourceNotFoundException("Não existe com este id ")));
              return parkingSpotID.get().add(linkTo(methodOn(ParkingSpotController.class).getAllParkingSpots()).withRel("List all ParkSpots"));

    }


    public List<ParkingSpotModel> getAll() {

        // refatorar para stream talves

        List<ParkingSpotModel> getAllparking = parkingSpotRepository.findAll();
        for (ParkingSpotModel parkingSpotModel : getAllparking) {
            UUID parkingID = parkingSpotModel.getId();
            parkingSpotModel.add(linkTo(methodOn(ParkingSpotController.class).getParkingSpotById(parkingID)).withSelfRel());
        }
        return Optional.of((getAllparking)).orElseThrow(() -> new ResourceNotFoundException("lista vazia"));
    }


    @Transactional
    public Map<String, Boolean> deleteParkingSpot(UUID parkingID)  {
        Optional<ParkingSpotModel> parkingSpotID = Optional.ofNullable(parkingSpotRepository.findById(parkingID).orElseThrow(() -> new ResourceNotFoundException("Não tem para deletar ")));
        parkingSpotRepository.delete(parkingSpotID.get());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Transactional
    public ParkingSpotModel updateParkingSpot(UUID parkingID, @Valid ParkingSpotDto parkingSpotDetails) {
        // fazer de outro jeito
        // return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
        //  parkingSpotModel.setParkingSpotNumber(parkingSpotDetails.getParkingSpotNumber());
        //  parkingSpotModel.setLicensePlateCar(parkingSpotDetails.getLicensePlateCar());
        //  parkingSpotModel.setBrandCar(parkingSpotDetails.getBrandCar());
        //  parkingSpotModel.setModelCar(parkingSpotDetails.getModelCar());
        //  parkingSpotModel.setColorCar(parkingSpotDetails.getColorCar());
        //  parkingSpotModel.setResponsibleName(parkingSpotDetails.getResponsibleName());
        //  parkingSpotModel.setApartment(parkingSpotDetails.getApartment());
        //  parkingSpotModel.setBlock(parkingSpotDetails.getBlock());

        ParkingSpotModel parkingSpotModelOptional = parkingSpotRepository.findById(parkingID).orElseThrow(() -> new ResourceNotFoundException("Not Found:: " + parkingID));
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDetails, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.getRegistrationDate());
        return parkingSpotRepository.save(parkingSpotModel);
    }
}


