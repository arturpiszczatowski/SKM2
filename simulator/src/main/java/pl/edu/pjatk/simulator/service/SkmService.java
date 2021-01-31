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
import java.util.stream.Collectors;

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

            Station currentStation = train.getCurrentStation();
            int currentPauseTime = train.getCurrentPauseTime();

            if (currentPauseTime > 0) {
                train.setCurrentPauseTime(currentPauseTime-1);
            } else {
                int nextStationModifier = train.isGoingToGdansk() ? 1 : -1;

                Long newId = currentStation.getId() + nextStationModifier;

                if(newId > 0 && newId < 16) {
                    train.setCurrentStation(stationRepository.getOne(newId));
                    train.setCurrentPauseTime(train.getCurrentStation().getPausetime());

                    train.getCompartments().forEach(compartment -> compartment.disembark(currentStation));
                    train.getCompartments().forEach(compartment -> {
                        List<Person> people = PersonGenerator.generatePeople(currentStation, stationRepository);
                        people.forEach((person) -> compartment.embark(person, compartmentRepository));
                        people = people.stream().filter(person -> person.getCompartment() != null).collect(Collectors.toList());
                        personRepository.saveAll(people);
                    });
                }

                currentPauseTime = train.getCurrentPauseTime();
                if(currentPauseTime > 1) {
                    train.setGoingToGdansk(!train.isGoingToGdansk());
                }
            }
        });
        trainRepository.saveAll(trains);
    }
}
