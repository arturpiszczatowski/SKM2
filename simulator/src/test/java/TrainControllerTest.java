import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjatk.simulator.controller.TrainController;
import pl.edu.pjatk.simulator.service.TrainService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TrainController.class)
@RunWith(SpringRunner.class)
public class TrainControllerTest {

    @MockBean
    TrainService trainService;

    @Autowired
    MockMvc mockMvc;

    TrainController trainController;

    @Test
    public void getAllTrains() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trains").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(trainService).getAll();
    }
}
