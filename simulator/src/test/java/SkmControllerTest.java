import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjatk.simulator.controller.SkmController;
import pl.edu.pjatk.simulator.service.SkmService;

@SpringBootTest(classes = SkmController.class)
@AutoConfigureMockMvc
public class SkmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SkmService skmService;

    @Test
    public void moveForward() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/skm/go"));
        Mockito.verify(skmService).moveTimeForward();
    }
}
