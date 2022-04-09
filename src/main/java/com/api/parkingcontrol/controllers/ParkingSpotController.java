package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<ParkingSpotModel> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) throws DataIntegrityViolationException {
        return parkingSpotService.save(parkingSpotDto);
    }

    @GetMapping("parkingspot/{id}")
    public ResponseEntity<ParkingSpotModel> getParkingSpotById(@PathVariable (value = "id") UUID id) {
        return parkingSpotService.getParkingSpotById(id);
    }



    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){


        return parkingSpotService.getAll();
    }

}
