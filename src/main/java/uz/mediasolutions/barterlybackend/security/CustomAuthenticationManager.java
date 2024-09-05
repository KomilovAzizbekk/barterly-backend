package uz.mediasolutions.barterlybackend.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Arrays;

public class CustomAuthenticationManager implements AuthenticationManager {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationManager(AuthenticationProvider... providers) {
        this.authenticationManager = new ProviderManager(Arrays.asList(providers));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authenticationManager.authenticate(authentication);
    }
}
