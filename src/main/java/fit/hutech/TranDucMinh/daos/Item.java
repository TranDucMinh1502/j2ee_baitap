package fit.hutech.TranDucMinh.daos;

import lombok.*;
import fit.hutech.TranDucMinh.entities.Book;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Long bookId;
    private String title;
    private String author;
    private Double price;
    private int quantity;
    
    public Item(Book book) {
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.quantity = 1;
    }
    
    public Double getSubTotal() {
        return price * quantity;
    }
}