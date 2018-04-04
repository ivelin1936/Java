package softuni.gamestore.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.gamestore.demo.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByEmail(String email);
}
