package uz.mediasolutions.barterlybackend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.matches(Rest.PHONE_NUMBER_REGEX)) {
            return userRepository
                    .findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        } else {
            return userRepository
                    .findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        }
    }
}
