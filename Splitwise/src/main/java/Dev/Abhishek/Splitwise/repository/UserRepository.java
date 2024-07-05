package Dev.Abhishek.Splitwise.repository;

import Dev.Abhishek.Splitwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByEmail(String email);

}
