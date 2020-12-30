package pl.edu.pjatk.simulator.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.StationRepository;
import pl.edu.pjatk.simulator.util.PersonGenerator;

import java.util.List;

@Service
@ConfigurationProperties(prefix = "skm")
public class SkmService {
    TrainService trainService;
    StationRepository stationRepository;

    public SkmService(TrainService trainService) {
        this.trainService = trainService;
    }

    public void moveTimeForward() {
        List<Train> trains = trainService.getAll();
        trains.forEach(train -> {
            int currentPauseTime = train.getCurrentPauseTime();
            if (currentPauseTime > 0) {
                currentPauseTime--;
            } else {
                int nextStationModifier = train.isGoingToGdansk() ? 1 : -1;
                Station currentStation = train.getCurrentStation();
                train.setCurrentStation(stationRepository.getOne(currentStation.getId() + nextStationModifier));

                currentPauseTime = currentStation.getPausetime();

                if(currentPauseTime > 0) {
                    train.setGoingToGdansk(!train.isGoingToGdansk());
                }

                train.getCompartments().forEach(compartment -> compartment.disembark(currentStation));
                train.getCompartments().forEach(compartment -> {
                    List<Person> people = PersonGenerator.generatePeople(currentStation);
                    people.forEach(compartment::embark);
                });
            }
        });
    }
}
