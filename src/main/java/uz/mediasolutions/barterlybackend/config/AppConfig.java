package uz.mediasolutions.barterlybackend.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.security.AdminAuthenticationProvider;
import uz.mediasolutions.barterlybackend.security.CustomAuthenticationManager;
import uz.mediasolutions.barterlybackend.security.CustomUserDetailsService;
import uz.mediasolutions.barterlybackend.security.UserAuthenticationProvider;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class AppConfig {

    private final UserRepository userRepository;
//    private final CustomUserDetailsService userDetailsService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

//    @Bean
//    public CustomUserDetailsService userDetailsService() {
//        return userDetailsService;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(userAuthenticationProvider())
//                .authenticationProvider(adminAuthenticationProvider());
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationProvider userAuthenticationProvider() {
//        return new UserAuthenticationProvider(userDetailsService);
//    }
//
//    @Bean
//    public AuthenticationProvider adminAuthenticationProvider() {
//        return new AdminAuthenticationProvider(userDetailsService, passwordEncoder());
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return new CustomAuthenticationManager(userAuthenticationProvider(), adminAuthenticationProvider());
//    }

}
