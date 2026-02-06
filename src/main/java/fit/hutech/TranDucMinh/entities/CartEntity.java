package fit.hutech.TranDucMinh.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class CartEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartItemEntity> items = new ArrayList<>();
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (items == null) {
            items = new ArrayList<>();
        }
    }
    
    /**
     * Thêm item vào cart
     * @param item CartItemEntity to add
     * @throws IllegalArgumentException if item is null
     */
    public void addItem(CartItemEntity item) {
        if (item == null) {
            throw new IllegalArgumentException("Item không được null");
        }
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setCart(this);
    }
    
    /**
     * Xóa item khỏi cart
     * @param item CartItemEntity to remove
     */
    public void removeItem(CartItemEntity item) {
        if (item != null && items != null) {
            items.remove(item);
            item.setCart(null);
        }
    }
    
    /**
     * Xóa toàn bộ items trong cart
     */
    public void clearItems() {
        if (items != null) {
            items.clear();
        }
    }
}