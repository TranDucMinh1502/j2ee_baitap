package fit.hutech.TranDucMinh.repositories;

import fit.hutech.TranDucMinh.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByCartId(Long cartId);
    void deleteByCartId(Long cartId);
}