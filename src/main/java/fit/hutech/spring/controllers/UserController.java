package fit.hutech.spring.controllers;
import fit.hutech.spring.entities.User;
import fit.hutech.spring.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Display login page
     */
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    /**
     * Display registration form
     */
    @GetMapping("/register")
    public String showRegisterForm(@NotNull Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    /**
     * Handle user registration with validation
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
            @NotNull BindingResult bindingResult,
            Model model) {
        
        // 1. Check validation errors
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("user", user);
            return "user/register";
        }

        // 2. Check if username already exists
        if (userService.findByUsername(user.getUsername())) {
            log.warn("Registration failed - username already exists: {}", user.getUsername());
            model.addAttribute("error", "Username already exists. Please choose another username.");
            model.addAttribute("user", user);
            return "user/register";
        }

        // 3. Check if email already exists
        if (userService.findByEmail(user.getEmail())) {
            log.warn("Registration failed - email already exists: {}", user.getEmail());
            model.addAttribute("error", "Email already exists. Please use a different email.");
            model.addAttribute("user", user);
            return "user/register";
        }

        try {
            // 4. Save user with encrypted password
            userService.save(user);
            
            // 5. Assign default role
            userService.setDefaultRole(user.getUsername());
            
            log.info("User registered successfully: username={}, email={}", 
                user.getUsername(), user.getEmail());
            
            // 6. Redirect to login with success message
            model.addAttribute("success", "Registration successful! Please log in.");
            return "redirect:/login?success";
            
        } catch (DataIntegrityViolationException e) {
            log.error("Registration failed - data integrity violation: {}", user.getUsername());
            model.addAttribute("error", "An account with this username or email already exists. Please try another.");
            model.addAttribute("user", user);
            return "user/register";
        } catch (Exception e) {
            log.error("Registration error for username: {}", user.getUsername(), e);
            model.addAttribute("error", "Registration failed. Please try again later.");
            model.addAttribute("user", user);
            return "user/register";
        }
    }
}