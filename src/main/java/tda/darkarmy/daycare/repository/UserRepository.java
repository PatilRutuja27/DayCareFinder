package tda.darkarmy.daycare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tda.darkarmy.daycare.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
