package fit.hutech.spring.controllers;
import fit.hutech.spring.entities.Book;
import fit.hutech.spring.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
private final BookService bookService;
@GetMapping
public String showAllBooks(@NotNull Model model) {
model.addAttribute("books", bookService.getAllBooks());
return "book/list";
}
}