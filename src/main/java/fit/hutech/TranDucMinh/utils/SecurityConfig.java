package fit.hutech.TranDucMinh.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fit.hutech.TranDucMinh.entities.User;
import fit.hutech.TranDucMinh.services.CartService;
import fit.hutech.TranDucMinh.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityConfig extends SavedRequestAwareAuthenticationSuccessHandler {
    
    private final CartService cartService;
    private final UserService userService;
    
    public SecurityConfig(@Lazy CartService cartService, @Lazy UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
        
        // Load giỏ hàng từ database vào session
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            try {
                User user = userService.findByUsername(userDetails.getUsername());
                cartService.loadCartFromDatabase(user, request.getSession());
            } catch (Exception e) {
                // Ignore if user not found
            }
        }
        
        // Admin luôn vào thẳng dashboard, user luôn về trang chủ
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        if (isAdmin) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}