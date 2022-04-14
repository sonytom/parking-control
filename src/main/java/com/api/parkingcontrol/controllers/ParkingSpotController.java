package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.ErrorDetails;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
@Controller
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @ApiOperation(value = "Return all parking Slots",
            responseContainer = "Mono", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns all parking slots"),
            @ApiResponse(code = 500, message = "Internal error occurred", response = ErrorDetails.class)
    })
    @GetMapping
    public List<ParkingSpotModel> getAllParkingSpots()  {
        return parkingSpotService.getAll();
    }



    @ApiOperation(value = "Return parkingspot by id",
            responseContainer = "Mono", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns parkingspot by id"),
            @ApiResponse(code = 500, message = "Internal error occurred", response = ErrorDetails.class)
    })
    @GetMapping("parkingspot-id/{parkingID}")
    public ParkingSpotModel getParkingSpotById(@Valid @PathVariable (value = "parkingID") UUID parkingID)  {
        return parkingSpotService.getParkingSpotById(parkingID);
    }


    @ApiOperation(value = "post and save parking spot",
            responseContainer = "Mono", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns a parkingspot model saved on db"),
            @ApiResponse(code = 500, message = "Internal error occurred", response = ErrorDetails.class)
    })
    @PostMapping
    public ParkingSpotModel saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto)  {
        return parkingSpotService.save(parkingSpotDto);
    }


    @ApiOperation(value = "delete one parking spot by id",
            responseContainer = "Mono", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns boolean if deleted"),
            @ApiResponse(code = 500, message = "Internal error occurred", response = ErrorDetails.class)
    })
    @DeleteMapping("parkingspot-id/{parkingID}")
    public Map<String, Boolean> deleteParkingSpot(@Valid @PathVariable(value = "parkingID") UUID parkingID)  {
        return parkingSpotService.deleteParkingSpot(parkingID);
    }


    @ApiOperation(value = "alter one parking spotmodel by id",
            responseContainer = "Mono", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "return parkingspot saved on db with id and date"),
            @ApiResponse(code = 500, message = "Internal error occurred", response = ErrorDetails.class)
    })
    @PutMapping("parkingspot-id/{parkingID}")
    public ParkingSpotModel updateParkingspot(@PathVariable(value = "parkingID") UUID parkingID, @Valid @RequestBody ParkingSpotDto parkingSpotDetails)  {
        return  parkingSpotService.updateParkingSpot(parkingID, parkingSpotDetails);
    }
}
