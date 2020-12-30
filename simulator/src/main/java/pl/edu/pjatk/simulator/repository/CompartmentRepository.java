package pl.edu.pjatk.simulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.simulator.model.Compartment;

@Repository
public interface CompartmentRepository extends JpaRepository<Compartment, Long> {
}
