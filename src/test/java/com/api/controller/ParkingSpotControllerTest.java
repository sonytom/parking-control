package com.api.controller;

import com.api.parkingcontrol.controllers.ParkingSpotController;
import com.api.parkingcontrol.services.ParkingSpotService;
import com.api.stub.ParkingModelStub;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSpotControllerTest {

    @Mock
    private ParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @Test
    @DisplayName("Validate get all Success")
    public void getAllParkingSpotssSccess() {
        final var expectGet = ParkingModelStub.createStubOfParkingSpotmodel();

        Mockito.when(parkingSpotService.getAll()).thenReturn(expectGet);
        final var response = parkingSpotController.getAllParkingSpots();
        Assertions.assertThat(expectGet).isEqualTo(response);
    }





}
