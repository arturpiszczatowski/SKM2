import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.pjatk.simulator.repository.StationRepository;
import pl.edu.pjatk.simulator.service.SkmService;
import pl.edu.pjatk.simulator.service.TrainService;

@SpringBootTest(classes = SkmService.class)
public class SkmServiceTest {

    @MockBean
    StationRepository stationRepository;

    @MockBean
    TrainService trainService;

    @Autowired
    SkmService skmService;

    @BeforeEach
    public void setup() {
        skmService = new SkmService(trainService);
    }
}
