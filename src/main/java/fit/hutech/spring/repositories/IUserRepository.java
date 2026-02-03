package fit.hutech.spring.repositories;
import fit.hutech.spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);  // Dùng Optional
}