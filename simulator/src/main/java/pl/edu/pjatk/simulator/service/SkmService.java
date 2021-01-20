package pl.edu.pjatk.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.PersonRepository;
import pl.edu.pjatk.simulator.repository.StationRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;
import pl.edu.pjatk.simulator.util.PersonGenerator;

import java.util.List;

@Service
@ConfigurationProperties(prefix = "skm")
public class SkmService {

    TrainService trainService;

    @Autowired
    CompartmentRepository compartmentRepository;

    @Autowired
    StationRepository stationRepository;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    PersonRepository personRepository;

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
                Long newId = currentStation.getId() + nextStationModifier;

                if(newId > 0)

                train.setCurrentStation(stationRepository.getOne(currentStation.getId() + nextStationModifier));

                currentPauseTime = currentStation.getPausetime();

                if(currentPauseTime > 0) {
                    train.setGoingToGdansk(!train.isGoingToGdansk());
                }

                train.getCompartments().forEach(compartment -> compartment.disembark(currentStation));
                train.getCompartments().forEach(compartment -> {
                    List<Person> people = PersonGenerator.generatePeople(currentStation, stationRepository);
                    people.forEach((person)->compartment.embark(person, compartmentRepository));
                    //personRepository.saveAll(people);
                });
            }
        });
        trainRepository.saveAll(trains);
    }
}
