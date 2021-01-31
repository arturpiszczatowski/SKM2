package controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjatk.simulator.controller.TrainController;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.service.TrainService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TrainController.class)
@ContextConfiguration(classes = {TrainController.class})
@WithMockUser(authorities = {"ROLE_ADMIN"})
public class TrainControllerTest {

    @MockBean
    TrainService trainService;

    @Autowired
    TrainController trainController;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void getAllTrains() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trains").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(trainService).getAll();
    }

    @Test
    public void getTrainById() throws Exception {
        Station station = new Station();
        station.setName("Narnia");
        Train train = Mockito.spy(new Train());
        train.setId(1L);
        train.setCurrentStation(station);
        Mockito.when(trainService.getById(1L)).thenReturn(train);
        Mockito.when(train.getCompartments()).thenReturn(List.of(new Compartment(), new Compartment()));
        mockMvc.perform(MockMvcRequestBuilders.get("/trains/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
        Mockito.verify(trainService).getById(1L);
    }

    @Test
    public void getTrainByIdIsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trains/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        Mockito.verify(trainService).getById(999L);
    }
}
