package com.api.parkingcontrol.services;

import com.api.parkingcontrol.controllers.ParkingSpotController;
import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.validation.ParkingSpotValidationImpl;
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
    final ParkingSpotValidationImpl parkingSpotValidation;


    public ParkingSpotServiceImpl(ParkingSpotRepository parkingSpotRepository, ParkingSpotValidationImpl parkingSpotValidation) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.parkingSpotValidation = parkingSpotValidation;
    }

    @Transactional
    @Override
    public ParkingSpotModel save(ParkingSpotDto parkingSpotDto) {
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));


            return Optional.of(parkingSpotRepository.save(parkingSpotModel)).orElseThrow(() -> new DataIntegrityViolationException("lista vazia"));


           }
/*
    private Optional<SomeObject> getObjFromService(Service someService) {
        try {
            return Optional.of(someService.getSomeObject());
        } catch (ServiceException e) {
            LOG.error("Something nasty happened", e);
        }
        return Optional.empty();
    }
*/

    public ParkingSpotModel getParkingSpotById(UUID parkingID) {


        return parkingSpotRepository.findById(parkingID).orElseThrow(() -> new ResourceNotFoundException("funciona?"));


    }


    public List<ParkingSpotModel> getAll() {
        List<ParkingSpotModel> getAllparking = parkingSpotRepository.findAll();
        for (ParkingSpotModel parkingSpotModel : getAllparking) {
            UUID parkingID = parkingSpotModel.getId();
            parkingSpotModel.add(linkTo(methodOn(ParkingSpotController.class).getParkingSpotById(parkingID)).withSelfRel());
        }
        return Optional.of((getAllparking)).orElseThrow(() -> new ResourceNotFoundException("lista vazia"));
    }


    @Transactional
    public Map<String, Boolean> deleteParkingSpot(UUID parkingID) {
        Optional<ParkingSpotModel> parkingSpotID = Optional.ofNullable(parkingSpotRepository.findById(parkingID).orElseThrow(() -> new ResourceNotFoundException("Não tem para deletar ")));
        parkingSpotRepository.delete(parkingSpotID.get());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Transactional
    public ParkingSpotModel updateParkingSpot(UUID parkingID, @Valid ParkingSpotDto parkingSpotDetails) {
        ParkingSpotModel parkingSpotModelOptional = parkingSpotRepository.findById(parkingID).orElseThrow(() -> new ResourceNotFoundException("Not Found:: " + parkingID));
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDetails, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.getRegistrationDate());
        return parkingSpotRepository.save(parkingSpotModel);
    }
}


