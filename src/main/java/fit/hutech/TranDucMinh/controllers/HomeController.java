package fit.hutech.TranDucMinh.controllers;

import lombok.RequiredArgsConstructor;
import fit.hutech.TranDucMinh.services.BookService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final BookService bookService;
    
    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        // Nếu đã đăng nhập, redirect đến trang danh sách
        if (authentication != null && authentication.isAuthenticated() 
            && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/books";
        }
        
        // Nếu chưa đăng nhập, hiển thị trang chủ với sản phẩm
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }
}