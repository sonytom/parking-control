package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.validation.ParkSpotValidation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ParkingSpotService implements ParkingSpotServiceInterface {

    final ParkingSpotRepository parkingSpotRepository;

    @Autowired
    ParkSpotValidation parkSpotValidation;



    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }


public List<ParkingSpotModel> findAll(){
        return parkingSpotRepository.findAll();
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
}

