package pl.edu.pjatk.simulator.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.StationRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;

import java.util.Optional;

@Service
public class TrainService extends CrudService<Train>{
    private CompartmentRepository compartmentRepository;
    private StationRepository stationRepository;

    public TrainService(TrainRepository trainRepository, CompartmentRepository compartmentRepository, StationRepository stationRepository) {
        super(trainRepository);
        this.compartmentRepository = compartmentRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public Train createOrUpdate(Train updateEntity) {

        if(updateEntity.getId() == null) {
            return repository.save(updateEntity);
        }

        Optional<Train> presentEntity = repository.findById(updateEntity.getId());

        if(presentEntity.isPresent()){
            Train updateTrain = presentEntity.get();

            updateTrain.setCompartments(updateTrain.getCompartments());
            updateTrain.setCurrentStation(updateTrain.getCurrentStation());
            updateTrain.setGoingToGdansk(updateTrain.isGoingToGdansk());

            Train updatedTrain = repository.save(updateTrain);
            return updatedTrain;
        } else {
            updateEntity = repository.save(updateEntity);
            return updateEntity;
        }
    }
}
