//package uz.mediasolutions.barterlybackend.custom;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import uz.mediasolutions.barterlybackend.exceptions.RestException;
//
//@Component
//@RequiredArgsConstructor
//public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
//
//    private final UsernameUserDetailsService userDetailsService;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getPrincipal().toString();
//        String password = authentication.getCredentials().toString();
//
//        UserDetails user = userDetailsService.loadUserByUsername(username);
//
//        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
//            throw RestException.restThrow("Bad credentials", HttpStatus.BAD_REQUEST);
//        }
//        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
