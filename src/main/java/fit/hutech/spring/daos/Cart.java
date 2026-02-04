package fit.hutech.spring.daos;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {
    private List<Item> cartItems = new ArrayList<>();
    
    /**
     * Add item to cart or update quantity if already exists
     */
    public void addItems(Item item) {
        if (item == null || item.getBookId() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid item");
        }
        
        boolean isExist = cartItems.stream()
                .filter(i -> Objects.equals(i.getBookId(), item.getBookId()))
                .findFirst()
                .map(i -> {
                    int newQuantity = i.getQuantity() + item.getQuantity();
                    i.setQuantity(newQuantity);
                    return true;
                })
                .orElse(false);
        
        if (!isExist) {
            cartItems.add(item);
        }
    }
    
    /**
     * Remove item from cart by book id
     */
    public void removeItems(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }
        cartItems.removeIf(item -> Objects.equals(item.getBookId(), bookId));
    }
    
    /**
     * Update item quantity in cart
     */
    public void updateItems(Long bookId, int quantity) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }
        
        if (quantity <= 0) {
            removeItems(bookId);
        } else {
            cartItems.stream()
                    .filter(item -> Objects.equals(item.getBookId(), bookId))
                    .forEach(item -> item.setQuantity(quantity));
        }
    }
}