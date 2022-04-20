package com.api.stub;

import com.api.parkingcontrol.models.ParkingSpotModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//  {
//      "parkingSpotNumber":"1759",
//          "licensePlateCar":"FEDTGB7",
//          "brandCar":"BMW",
//          "modelCar":"ix",
//          "colorCar":"Black",
//          "responsibleName":"Maria Oliveira",
//          "apartment":"5",
//          "block":"6"
//  }

public class ParkingModelStub {

    public static List<ParkingSpotModel> createStubOfParkingSpotmodel() {
        return List.of(ParkingSpotModel.builder()
                .id(UUID.randomUUID())
                .parkingSpotNumber("1759")
                .licensePlateCar("AEDFR")
                .brandCar("BMW")
                .modelCar("Z4")
                .colorCar("Black")
                .responsibleName("Eduardo Jose")
                .apartment("1")
                .block("4")
                .build());
    }

    public static ParkingSpotModel stubOfParkingSpotmodel() {
        return ParkingSpotModel.builder().id(UUID.randomUUID())
                .parkingSpotNumber("1759")
                .licensePlateCar("AEDFR")
                .brandCar("BMW")
                .modelCar("Z4")
                .colorCar("Black")
                .responsibleName("Eduardo Jose")
                .apartment("1")
                .block("4")
                .build();
    }

    public static ParkingSpotModel updateStubOfParkingSpotmodel() {
        return ParkingSpotModel.builder().id(UUID.randomUUID())
                .parkingSpotNumber("1759")
                .licensePlateCar("AEDFR")
                .brandCar("BMW")
                .modelCar("Z4")
                .colorCar("Black")
                .responsibleName("Eduardo Jose")
                .apartment("1")
                .block("4")
                .build();
    }
}
