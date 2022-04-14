package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.exeption.DataIntegrityViolationException;
import com.api.parkingcontrol.exeption.NoSuchElementException;
import com.api.parkingcontrol.exeption.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//  @RestController
//  @RequestMapping("/v1/search")
//  @Api(tags = "Search controller")
//  @AllArgsConstructor
//  public class SearchController {
//
//      private final SearchContractFacade searchContractFacade;
//
//      @ApiOperation(value = "Search items by  term",
//              response = ItemSuggestionContractResponse.class,
//              responseContainer = "Mono", produces = "application/json")
//      @ApiResponses({
//              @ApiResponse(code = 200, message = "Returns item search result"),
//              @ApiResponse(code = 400, message = "Bad Request", response = ErrorInfo.class),
//              @ApiResponse(code = 404, message = " Not Found", response = ErrorInfo.class),
//              @ApiResponse(code = 500, message = "Internal error occurred", response = ErrorInfo.class)
//      })
//      @PostMapping
//      public Mono<SearchResponse> search(@NotNull @Valid @RequestBody SearchRequest searchRequest) {
//          return searchContractFacade.search(searchRequest);
//      }
//  }


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    public List<ParkingSpotModel> getAllParkingSpots() {
        return parkingSpotService.getAll();
    }

    @GetMapping("parkingspot-id/{id}")
    public ParkingSpotModel getParkingSpotById(@PathVariable (value = "id") UUID parkingID)  {
        return parkingSpotService.getParkingSpotById(parkingID);
    }

    @PostMapping
    public ParkingSpotModel saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        return parkingSpotService.save(parkingSpotDto);
    }


    @DeleteMapping("parkingspot-id/{id}")
    public Map<String, Boolean> deleteParkingSpot(@PathVariable(value = "id") UUID parkingID)  {
        return parkingSpotService.deleteParkingSpot(parkingID);
    }

    @PutMapping("parkingspot-id/{id}")
    public ParkingSpotModel updateParkingspot(@PathVariable(value = "id") UUID parkingID, @Valid @RequestBody ParkingSpotDto parkingSpotDetails)  {
        return  parkingSpotService.updateParkingSpot(parkingID, parkingSpotDetails);
    }
}
