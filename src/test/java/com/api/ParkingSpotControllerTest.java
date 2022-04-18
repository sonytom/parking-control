package com.api;

import com.api.parkingcontrol.controllers.ParkingSpotController;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSpotControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();


    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @InjectMocks
    private ParkingSpotController parkingSpotController;
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
    ParkingSpotModel RECORD_1 = new ParkingSpotModel(UUID.randomUUID(),"1759","ASDQWER","Bmw","z4","black", LocalDateTime.now(ZoneId.of("UTC")),"Jose","232","1");

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(parkingSpotController).build();
    }


    @Test
    public void getAllParkingSpots_success() throws Exception {
        List<ParkingSpotModel> records = new ArrayList<>(Arrays.asList(RECORD_1));

        Mockito.when(parkingSpotRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/parking-spot")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$",hasSize(3)))
                    .andExpect(jsonPath("$[2].parkingSpotNumber",is("1759")));
    }


}
