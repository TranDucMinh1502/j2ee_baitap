package fit.hutech.spring.services;

import fit.hutech.spring.constants.Provider;
import fit.hutech.spring.constants.Role;
import fit.hutech.spring.entities.User;
import fit.hutech.spring.repositories.IRoleRepository;
import fit.hutech.spring.repositories.IUserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Check if username exists
     */
    public boolean findByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Check if email exists
     */
    public boolean findByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Save OAuth user from Google login
     */
    @Transactional
    public void saveOauthUser(String email, String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            var user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("123"));
            user.setProvider(Provider.GOOGLE.value);
            userRepository.save(user);

            setDefaultRole(username);
            log.info("OAuth user created: email={}, username={}", email, username);
        }
    }

    /**
     * Save user with encrypted password (for registration)
     */
    @Transactional(isolation = Isolation.SERIALIZABLE,
            rollbackFor = {Exception.class, Throwable.class})
    public void save(@NotNull User user) {
        // Encrypt password before saving
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        
        // Set default provider if not set
        if (user.getProvider() == null || user.getProvider().isBlank()) {
            user.setProvider(Provider.LOCAL.value);
        }
        
        userRepository.save(user);
        log.info("User saved successfully: username={}", user.getUsername());
    }

    /**
     * Assign default role to user
     */
    @Transactional(isolation = Isolation.SERIALIZABLE,
            rollbackFor = {Exception.class, Throwable.class})
    public void setDefaultRole(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        user.getRoles()
                .add(roleRepository
                        .findRoleById(Role.USER.value));
        
        log.debug("Default role assigned to user: {}", username);
    }

    /**
     * Load user by username for Spring Security authentication
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Login attempt with non-existent username: {}", username);
                    return new UsernameNotFoundException("Username or password is incorrect");
                });
        
        log.debug("User loaded for authentication: {}", username);
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}