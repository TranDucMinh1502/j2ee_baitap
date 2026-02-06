package fit.hutech.TranDucMinh.services;

import fit.hutech.TranDucMinh.entities.Book;
import fit.hutech.TranDucMinh.repositories.IBookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {Exception.class, Throwable.class})
public class BookService {
    private final IBookRepository bookRepository;
    
    /**
     * Lấy tất cả sách với category
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAllWithCategory();
    }
    
    /**
     * Lấy sách với pagination
     */
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    /**
     * Tìm sách theo ID
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    
    /**
     * Tìm kiếm sách theo từ khóa
     */
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }
        return bookRepository.searchByTitleOrAuthor(keyword.trim());
    }
    
    /**
     * Lấy sách theo category
     */
    public List<Book> getBooksByCategory(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId);
    }
    
    /**
     * Lấy sách theo khoảng giá
     */
    public List<Book> getBooksByPriceRange(Double minPrice, Double maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    /**
     * Lấy sách còn hàng
     */
    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }
    
    /**
     * Lưu sách mới hoặc cập nhật
     */
    public void saveBook(Book book) {
        if (book.getStock() == null) {
            book.setStock(0);
        }
        bookRepository.save(book);
        log.info("Saved book: {}", book.getTitle());
    }
    
    /**
     * Cập nhật thông tin sách
     */
    public void updateBook(Long id, @NotNull Book book) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách với ID: " + id));
        
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setCategory(book.getCategory());
        existingBook.setDescription(book.getDescription());
        existingBook.setStock(book.getStock());
        existingBook.setImageUrl(book.getImageUrl());
        
        bookRepository.save(existingBook);
        log.info("Updated book ID: {}", id);
    }
    
    /**
     * Xóa sách
     */
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy sách với ID: " + id);
        }
        bookRepository.deleteById(id);
        log.info("Deleted book ID: {}", id);
    }
    
    /**
     * Đếm tổng số sách
     */
    public long countBooks() {
        return bookRepository.count();
    }
    
    /**
     * Kiểm tra tồn kho
     */
    public boolean isInStock(Long bookId, int quantity) {
        return bookRepository.findById(bookId)
                .map(book -> book.getStock() != null && book.getStock() >= quantity)
                .orElse(false);
    }
    
    /**
     * Giảm số lượng tồn kho
     */
    public void decreaseStock(Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách với ID: " + bookId));
        
        if (book.getStock() == null || book.getStock() < quantity) {
            throw new IllegalStateException("Không đủ hàng trong kho cho sách: " + book.getTitle());
        }
        
        book.setStock(book.getStock() - quantity);
        bookRepository.save(book);
        log.info("Decreased stock for book ID {}: -{} (remaining: {})", bookId, quantity, book.getStock());
    }
    
    /**
     * Tăng số lượng tồn kho
     */
    public void increaseStock(Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách với ID: " + bookId));
        
        book.setStock((book.getStock() != null ? book.getStock() : 0) + quantity);
        bookRepository.save(book);
        log.info("Increased stock for book ID {}: +{} (total: {})", bookId, quantity, book.getStock());
    }
}