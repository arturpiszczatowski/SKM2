package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;
import pl.edu.pjatk.simulator.util.PersonGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trains")
public class Train implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Compartment> compartments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private Station currentStation;

    @Column(name = "direction")
    private boolean goingToGdansk;

    private int trainCurrentPauseTime;

    public Train(){
        setCurrentPauseTime();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(List<Compartment> compartments) {
        this.compartments = compartments;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    public boolean isGoingToGdansk() {
        return goingToGdansk;
    }

    public void setGoingToGdansk(boolean goingToGdansk) {
        this.goingToGdansk = goingToGdansk;
    }

    public int getCurrentPauseTime() {
        return trainCurrentPauseTime;
    }

    public void setCurrentPauseTime() {
        this.trainCurrentPauseTime = this.currentStation.getPausetime();
    }

    public void move() {
        if (getCurrentPauseTime() > 0) {
            this.trainCurrentPauseTime--;
        } else {
            int nextStationModifier = goingToGdansk ? 1 : -1;
            Long nextStationId = this.currentStation.getId() + nextStationModifier;
            this.currentStation.setId(nextStationId);
            setCurrentPauseTime();

            trainCurrentPauseTime = getCurrentPauseTime();

            if (trainCurrentPauseTime > 0) {
                goingToGdansk = !goingToGdansk;
            }

            compartments.forEach(c -> c.disembark(this.currentStation));
            compartments.forEach(c -> {
                List<Person> people = PersonGenerator.generatePeople(this.currentStation);
                people.forEach(c::embark);
            });
        }
    }
}
