package pl.edu.pjatk.simulator.model;

import javax.persistence.*;

@Entity
@Table(name = "occupants")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private Station destination;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compartment_id", referencedColumnName = "id")
    private Compartment compartment;

    public Person(String firstName, String lastName, Station destination) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.destination = destination;
    }

    public Person(){

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Station getDestination() {
        return destination;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
}
