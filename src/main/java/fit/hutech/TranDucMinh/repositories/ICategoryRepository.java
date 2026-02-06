package fit.hutech.TranDucMinh.repositories;

import fit.hutech.TranDucMinh.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ICategoryRepository extends
JpaRepository<Category, Long> {
}