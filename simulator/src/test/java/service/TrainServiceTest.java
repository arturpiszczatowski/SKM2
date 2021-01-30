//package service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import pl.edu.pjatk.simulator.model.Station;
//import pl.edu.pjatk.simulator.model.Train;
//import pl.edu.pjatk.simulator.repository.CompartmentRepository;
//import pl.edu.pjatk.simulator.repository.StationRepository;
//import pl.edu.pjatk.simulator.repository.TrainRepository;
//import pl.edu.pjatk.simulator.service.TrainService;
//
//import java.util.Optional;
//
//@SpringBootTest(classes = TrainService.class)
//public class TrainServiceTest {
//    public TrainService trainService;
//
//    @MockBean
//    private TrainRepository trainRepository;
//
//    @MockBean
//    private CompartmentRepository compartmentRepository;
//
//    @MockBean
//    private StationRepository stationReposiotry;
//
//
//    @BeforeEach
//    private void setUp(){
//        trainService = new TrainService(trainRepository, compartmentRepository, stationReposiotry);
//    }
//
//    @Test
//    public void createNewTrainWithoutID() {
//        Station station = stationReposiotry.getOne(2L);
//        Mockito.when(station.getPausetime()).thenReturn(0);
//        Train train = new Train();
//        train.setCurrentStation(station);
//        trainService.createOrUpdate(train);
//        Mockito.verify(trainRepository).save(train);
//    }
//
//    @Test
//    public void createNewTrainWithId() {
//        Station station = stationReposiotry.getOne(3L);
//        station.setPausetime(0);
//        Train train = new Train();
//        train.setId(0L);
//        train.setCurrentStation(station);;
//        Mockito.when(trainRepository.findById(0L)).thenReturn(Optional.empty());
//        trainService.createOrUpdate(train);
//        Mockito.verify(trainRepository).save(train);
//    }
//
//    @Test
//    public void updateTrain() {
//        Station station = stationReposiotry.getOne(4L);
//        station.setPausetime(0);
//        Train train = Mockito.spy(new Train());
//        train.setId(0L);
//        train.setCurrentStation(station);
//        Mockito.when(train.getId()).thenReturn(0L);
//        Mockito.when(trainRepository.findById(0L)).thenReturn(Optional.of(train));
//        trainService.createOrUpdate(train);
//        Mockito.verify(trainRepository).findById(0L);
//        Mockito.verify(trainRepository).save(train);
//    }
//}
