package fit.hutech.TranDucMinh.repositories;

import fit.hutech.TranDucMinh.entities.CartEntity;
import fit.hutech.TranDucMinh.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUser(User user);
    Optional<CartEntity> findByUserId(Long userId);
    void deleteByUser(User user);
}