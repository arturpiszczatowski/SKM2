package pl.edu.pjatk.simulator.model;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.service.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="compartments")
public class Compartment implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    private Train train;

    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Person> occupants;

    public Compartment(){

    }

    public Long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Collection<Person> getOccupants() {
        return occupants;
    }

    public void embark(Person person, CompartmentRepository compartmentRepository) {
        if (occupants.size() < capacity) {
            occupants.add(person);
            person.setCompartment(compartmentRepository.getOne(getId()));
        }
    }

    public void disembark(Station station) {
        List<Person> leaving = occupants.stream()
                .filter(p -> p.getDestination().equals(station))
                .collect(Collectors.toList());
        occupants.removeAll(leaving);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOccupants(List<Person> occupants) {
        this.occupants = occupants;
    }

    @Override
    public String toString() {
        return "Compartment{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", train=" + train.getId() +
                ", occupants=" + occupants +
                '}';
    }
}
