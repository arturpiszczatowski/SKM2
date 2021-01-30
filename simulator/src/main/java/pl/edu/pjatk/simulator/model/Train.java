package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;

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

    @Column(name = "current_pause_time")
    private int currentPauseTime;

    public Train(){
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
        return currentPauseTime;
    }

    public void setCurrentPauseTime(int pauseTime) {
        this.currentPauseTime = pauseTime;
    }


    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", compartments=" + compartments.toString() +
                ", currentStation=" + currentStation.getName() +
                ", goingToGdansk=" + goingToGdansk +
                ", currentPauseTime=" + currentPauseTime +
                '}';
    }
}
