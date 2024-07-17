//package uz.mediasolutions.barterlybackend.custom;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import uz.mediasolutions.barterlybackend.entity.User;
//import uz.mediasolutions.barterlybackend.exceptions.RestException;
//import uz.mediasolutions.barterlybackend.repository.UserRepository;
//
//@Service("usernameUserDetailsService")
//@RequiredArgsConstructor
//public class UsernameUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findFirstByEmailAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(username)
//                .orElseThrow(
//                        () -> RestException.restThrow("User not found", HttpStatus.BAD_REQUEST)
//                );
//       }
//}
