package com.api.parkingcontrol.integration;

import com.api.parkingcontrol.controllers.ParkingSpotController;
import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.services.ParkingSpotService;
import com.api.parkingcontrol.stub.ParkingSpotBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//O Mockito é um framework de Test and Spy

//Spy: cria uma instancia de uma classe, que você pode mockar ou chamar
// os metodos reais. É uma alternativa ao InjectMocks, quando é preciso
// mockar metodos da propria classe que esta sendo testada.


@RunWith(SpringRunner.class)
@WebMvcTest(ParkingSpotController.class)
public class ParkingSpotControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParkingSpotService service;



    @Test
    public void httpClientExample() throws Exception {

        var teste = new ParkingSpotBuilder().withParkingSpotNumber("o4").buildModel();

        given(service.getAll()).willReturn(List.of(teste));


               mvc.perform(get("/parking-spot")
                       .contentType(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$[0].parkingSpotNumber").value("oi"));


    }

}
