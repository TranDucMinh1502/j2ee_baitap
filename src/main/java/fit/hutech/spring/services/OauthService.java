package fit.hutech.spring.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {

    private static final String OAUTH_ATTRIBUTE_EMAIL = "email";
    private static final String OAUTH_ATTRIBUTE_NAME = "name";
    
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        // Guard clause: validate OAuth2User
        if (oAuth2User == null) {
            log.warn("OAuth2User is null from provider: {}", 
                userRequest.getClientRegistration().getRegistrationId());
            return oAuth2User;
        }
        
        try {
            processOAuthUserRegistration(oAuth2User, userRequest);
        } catch (Exception e) {
            log.error("Failed to save OAuth user from provider: {}", 
                userRequest.getClientRegistration().getRegistrationId(), e);
            // Don't throw exception - allow OAuth2 login to proceed
            // User registration failure is not critical for login
        }
        
        return oAuth2User;
    }

    @Transactional
    protected void processOAuthUserRegistration(OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
        String email = oAuth2User.getAttribute(OAUTH_ATTRIBUTE_EMAIL);
        String username = oAuth2User.getAttribute(OAUTH_ATTRIBUTE_NAME);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // Validate required attributes
        if (isValidOAuthUser(email, username)) {
            userService.saveOauthUser(email, username);
            log.info("OAuth user registered successfully - provider={}, username={}, email={}", 
                provider, username, email);
        } else {
            log.warn("OAuth user missing required attributes - provider={}, email={}, username={}", 
                provider, email, username);
        }
    }

    private boolean isValidOAuthUser(String email, String username) {
        return email != null && !email.isBlank() && 
               username != null && !username.isBlank();
    }
}