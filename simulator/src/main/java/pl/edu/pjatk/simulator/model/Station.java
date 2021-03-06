package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stations")
public class Station implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_name")
    private String name;

    @Column(name = "pause_time")
    private int pausetime;

    public int getPausetime() {
        return pausetime;
    }

    @OneToMany(mappedBy = "currentStation")
    private List<Train> trains;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }

    public void setPausetime(int pausetime) { this.pausetime = pausetime;}

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pausetime=" + pausetime +
                ", trains=" + trains.toString() +
                '}';
    }
}
