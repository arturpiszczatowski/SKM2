package pl.edu.pjatk.simulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.simulator.model.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
}
