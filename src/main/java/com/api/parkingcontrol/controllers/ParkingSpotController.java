package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.NoSuchElementException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots() throws ResourceNotFoundException, NoSuchElementException {
        return parkingSpotService.getAll();
    }

    @GetMapping("parkingspot-id/{id}")
    public ResponseEntity<ParkingSpotModel> getParkingSpotById(@PathVariable (value = "id") UUID parkingID) throws ResourceNotFoundException, NoSuchElementException {
        return parkingSpotService.getParkingSpotById(parkingID);
    }

    @PostMapping
    public ResponseEntity<ParkingSpotModel> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) throws DataIntegrityViolationException {
        return parkingSpotService.save(parkingSpotDto);
    }


    @DeleteMapping("parkingspot-id/{id}")
    public Map<String, Boolean> deleteParkingSpot(@PathVariable(value = "id") UUID parkingID) throws ResourceNotFoundException {
        return parkingSpotService.deleteParkingSpot(parkingID);
    }

    @PutMapping("parkingspot-id/{id}")
    public ResponseEntity<ParkingSpotModel> updateParkingspot(@PathVariable(value = "id") UUID parkingID, @Valid @RequestBody ParkingSpotDto parkingSpotDetails) throws ResourceNotFoundException {
        ParkingSpotModel updatedParkingspot = parkingSpotService.updateParkingSpot(parkingID, parkingSpotDetails);
        return ResponseEntity.ok(updatedParkingspot);
    }




}
