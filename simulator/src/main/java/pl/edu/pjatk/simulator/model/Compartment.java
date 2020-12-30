package pl.edu.pjatk.simulator.model;

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
    private static CompartmentRepository compartmentRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int capacity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    private Train train;

    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Person> occupants;

    public Compartment(){

    }

    public long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Collection<Person> getOccupants() {
        return occupants;
    }


    public void embark(Person person) {
        if (occupants.size() < capacity) {
            occupants.add(person);
            person.setCompartment(compartmentRepository.getOne(getId()));
        }
    }

    public void disembark(Station station) {
        List<Person> leaving = occupants.stream()
                .filter(p -> p.getDestination().equals(station.getName()))
                .collect(Collectors.toList());

        occupants.removeAll(leaving);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOccupants(List<Person> occupants) {
        this.occupants = occupants;
    }



}
