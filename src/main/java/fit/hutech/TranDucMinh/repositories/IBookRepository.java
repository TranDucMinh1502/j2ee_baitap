package fit.hutech.TranDucMinh.repositories;

import fit.hutech.TranDucMinh.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

	@Query("select b from Book b left join fetch b.category")
	List<Book> findAllWithCategory();
	
	// Tìm kiếm theo tên hoặc tác giả
	@Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Book> searchByTitleOrAuthor(@Param("keyword") String keyword);
	
	// Tìm theo danh mục
	List<Book> findByCategoryId(Long categoryId);
	
	// Tìm theo khoảng giá
	List<Book> findByPriceBetween(Double minPrice, Double maxPrice);
	
	// Tìm sách còn hàng
	@Query("SELECT b FROM Book b WHERE b.stock > 0")
	List<Book> findAvailableBooks();
	
	// Pagination
	Page<Book> findAll(Pageable pageable);
	Page<Book> findByCategoryId(Long categoryId, Pageable pageable);
}