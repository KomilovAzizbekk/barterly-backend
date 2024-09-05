package uz.mediasolutions.barterlybackend.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public UserAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getName();
        String otp = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);

        // checking otp logic
        if (otpIsValid(otp, userDetails)) {
            return new UsernamePasswordAuthenticationToken(userDetails, otp, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid otp");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean otpIsValid(String otp, UserDetails userDetails) {
        // SMS kodni tekshirish mexanizmi
        return true; // Placeholder uchun haqiqiy tekshirishni qo'shish kerak.
    }
}
