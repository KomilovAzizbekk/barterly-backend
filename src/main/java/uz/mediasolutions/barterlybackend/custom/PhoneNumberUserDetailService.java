//package uz.mediasolutions.barterlybackend.custom;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import uz.mediasolutions.barterlybackend.exceptions.RestException;
//import uz.mediasolutions.barterlybackend.repository.UserRepository;
//
//@Service("phoneNumberUserDetailsService")
//@RequiredArgsConstructor
//public class PhoneNumberUserDetailService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(username)
//                .orElseThrow(
//                        () -> RestException.restThrow("User not found", HttpStatus.BAD_REQUEST)
//                );
//    }
//}
