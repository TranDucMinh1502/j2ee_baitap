package fit.hutech.TranDucMinh.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.Objects;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "book")
public class Book {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(name = "title", length = 50, nullable = false)
private String title;
@Column(name = "author", length = 50, nullable = false)
private String author;
@Column(name = "price")
private Double price;

@Column(name = "description", columnDefinition = "TEXT")
private String description;

@Column(name = "stock")
@Builder.Default
private Integer stock = 0;

@Column(name = "image_url", length = 500)
private String imageUrl;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "category_id", referencedColumnName = "id")
@ToString.Exclude
private Category category;
@Override
public boolean equals(Object o) {
if (this == o) return true;
if (o == null || Hibernate.getClass(this) !=
Hibernate.getClass(o)) return false;
Book book = (Book) o;
return getId() != null && Objects.equals(getId(),
book.getId());
}
@Override
public int hashCode() {
return getClass().hashCode();
}
}