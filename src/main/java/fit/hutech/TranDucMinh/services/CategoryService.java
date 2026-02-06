package fit.hutech.TranDucMinh.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import fit.hutech.TranDucMinh.entities.Category;
import fit.hutech.TranDucMinh.repositories.ICategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {
    private final ICategoryRepository categoryRepository;
    
    /**
     * Lấy tất cả danh mục
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    /**
     * Tìm danh mục theo ID
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    /**
     * Lưu hoặc cập nhật danh mục
     */
    public Category saveCategory(Category category) {
        Category saved = categoryRepository.save(category);
        log.info("Saved category: {}", saved.getName());
        return saved;
    }
    
    /**
     * Cập nhật thông tin danh mục
     */
    public Category updateCategory(Long id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục với ID: " + id));
        
        existing.setName(category.getName());
        Category updated = categoryRepository.save(existing);
        log.info("Updated category ID: {}", id);
        return updated;
    }
    
    /**
     * Xóa danh mục
     */
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy danh mục với ID: " + id);
        }
        categoryRepository.deleteById(id);
        log.info("Deleted category ID: {}", id);
    }
    
    /**
     * Kiểm tra tồn tại theo tên
     */
    public boolean existsByName(String name) {
        return categoryRepository.findAll().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name.trim()));
    }
    
    /**
     * Đếm số sách trong danh mục
     */
    public long countBooksInCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> (long) category.getBooks().size())
                .orElse(0L);
    }
}