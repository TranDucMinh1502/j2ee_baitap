package fit.hutech.spring.controllers;

import fit.hutech.spring.daos.Item;
import fit.hutech.spring.entities.Book;
import fit.hutech.spring.services.BookService;
import fit.hutech.spring.services.CartService;
import fit.hutech.spring.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping
    public String showAllBooks(@NotNull Model model,
                               @RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "20") Integer pageSize,
                               @RequestParam(defaultValue = "id") String sortBy) {
        try {
            long totalBooks = bookService.countAllBooks();
            int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
            
            model.addAttribute("books", bookService.getAllBooks(pageNo, pageSize, sortBy));
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("categories", categoryService.getAllCategories());
            
            log.debug("Books list retrieved - page: {}, size: {}, total: {}", pageNo, pageSize, totalBooks);
            return "book/list";
        } catch (Exception e) {
            log.error("Error retrieving books list", e);
            model.addAttribute("error", "Error loading books. Please try again.");
            return "book/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.getBookById(id)
                .ifPresentOrElse(
                        book -> {
                            bookService.deleteBookById(id);
                            log.info("Book deleted: id={}, title={}", id, book.getTitle());
                        },
                        () -> {
                            log.warn("Delete book failed - book not found: id={}", id);
                            throw new IllegalArgumentException("Book not found");
                        });
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@NotNull Model model, @PathVariable long id) {
        var book = bookService.getBookById(id);
        if (book.isEmpty()) {
            log.warn("Edit book - book not found: id={}", id);
            throw new IllegalArgumentException("Book not found");
        }
        
        model.addAttribute("book", book.get());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/edit";
    }

    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("book") Book book,
                           @NotNull BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        }
        
        try {
            bookService.updateBook(book);
            log.info("Book updated: id={}, title={}", book.getId(), book.getTitle());
            return "redirect:/books";
        } catch (Exception e) {
            log.error("Error updating book: id={}", book.getId(), e);
            model.addAttribute("error", "Error updating book. Please try again.");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        }
    }

    @GetMapping("/add")
    public String addBookForm(@NotNull Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          @NotNull BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add";
        }
        
        try {
            bookService.addBook(book);
            log.info("Book added: title={}, author={}", book.getTitle(), book.getAuthor());
            return "redirect:/books";
        } catch (Exception e) {
            log.error("Error adding book", e);
            model.addAttribute("error", "Error adding book. Please try again.");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add";
        }
    }

    @GetMapping("/search")
    public String searchBook(@NotNull Model model,
                             @RequestParam String keyword,
                             @RequestParam(defaultValue = "0") Integer pageNo,
                             @RequestParam(defaultValue = "20") Integer pageSize) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return "redirect:/books";
            }
            
            model.addAttribute("books", bookService.searchBook(keyword.trim()));
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("keyword", keyword);
            model.addAttribute("categories", categoryService.getAllCategories());
            
            log.info("Book search performed: keyword={}", keyword);
            return "book/list";
        } catch (Exception e) {
            log.error("Error searching books: keyword={}", keyword, e);
            model.addAttribute("error", "Error searching books. Please try again.");
            return "book/list";
        }
    }

    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam long id,
                            @RequestParam String name,
                            @RequestParam double price,
                            @RequestParam(defaultValue = "1") int quantity) {
        try {
            if (quantity <= 0) {
                log.warn("Invalid quantity for add to cart: quantity={}", quantity);
                return "redirect:/books";
            }
            
            var cart = cartService.getCart(session);
            cart.addItems(new Item(id, name, price, quantity));
            cartService.updateCart(session, cart);
            
            log.info("Item added to cart: bookId={}, quantity={}", id, quantity);
            return "redirect:/books";
        } catch (Exception e) {
            log.error("Error adding to cart: bookId={}", id, e);
            return "redirect:/books";
        }
    }
}