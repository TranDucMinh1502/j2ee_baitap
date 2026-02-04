package fit.hutech.spring.controllers;

import fit.hutech.spring.services.BookService;
import fit.hutech.spring.viewmodels.BookGetVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RequiredArgsConstructor
public class ApiController {
    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        try {
            int page = pageNo == null ? 0 : Math.max(0, pageNo);
            int size = pageSize == null ? 20 : Math.max(1, pageSize);
            String sort = sortBy == null || sortBy.isBlank() ? "id" : sortBy;
            
            log.info("API: Fetching all books - page={}, size={}, sortBy={}", page, size, sort);
            
            List<BookGetVm> books = bookService.getAllBooks(page, size, sort)
                    .stream()
                    .map(BookGetVm::from)
                    .toList();
            
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            log.error("API: Error fetching all books", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch books");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/books/search")
    public ResponseEntity<?> searchBooks(@RequestParam(required = false) String keyword) {
        try {
            if (keyword == null || keyword.trim().isBlank()) {
                log.warn("API: Search books called with empty keyword");
                return ResponseEntity.ok(List.of());
            }
            
            String searchKeyword = keyword.trim();
            log.info("API: Searching books - keyword={}", searchKeyword);
            
            List<BookGetVm> books = bookService.searchBook(searchKeyword)
                    .stream()
                    .map(BookGetVm::from)
                    .toList();
            
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            log.error("API: Error searching books with keyword: {}", keyword, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to search books");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                log.warn("API: Invalid book id: {}", id);
                return ResponseEntity.badRequest().build();
            }
            
            log.info("API: Fetching book by id={}", id);
            return bookService.getBookById(id)
                    .map(book -> ResponseEntity.ok(BookGetVm.from(book)))
                    .orElseGet(() -> {
                        log.warn("API: Book not found - id={}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("API: Error fetching book with id: {}", id, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch book");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                log.warn("API: Invalid book id for delete: {}", id);
                return ResponseEntity.badRequest().build();
            }
            
            log.info("API: Deleting book - id={}", id);
            var book = bookService.getBookById(id);
            
            if (book.isEmpty()) {
                log.warn("API: Delete book failed - book not found: id={}", id);
                return ResponseEntity.notFound().build();
            }
            
            bookService.deleteBookById(id);
            log.info("API: Book deleted successfully - id={}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("API: Error deleting book with id: {}", id, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete book");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}