//package uz.mediasolutions.barterlybackend.custom;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import uz.mediasolutions.barterlybackend.exceptions.RestException;
//import uz.mediasolutions.barterlybackend.service.OtpService;
//
//@Component
//@RequiredArgsConstructor
//public class PhoneNumberOtpAuthenticationProvider implements AuthenticationProvider {
//
//    private final PhoneNumberUserDetailService userDetailsService;
//
//    private final OtpService otpService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String phoneNumber = authentication.getPrincipal().toString();
//        String otp = authentication.getCredentials().toString();
//
//        UserDetails user = userDetailsService.loadUserByUsername(phoneNumber);
//
//        if (user == null || !otpService.validateOtp(phoneNumber, otp)) {
//            throw RestException.restThrow("Invalid phone number or otp", HttpStatus.BAD_REQUEST);
//        }
//        return new UsernamePasswordAuthenticationToken(phoneNumber, otp, user.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
