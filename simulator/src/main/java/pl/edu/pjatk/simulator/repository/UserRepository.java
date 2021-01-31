package pl.edu.pjatk.simulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjatk.simulator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
