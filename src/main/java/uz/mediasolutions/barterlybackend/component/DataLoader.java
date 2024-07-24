package uz.mediasolutions.barterlybackend.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.*;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.enums.UserTypeEnum;
import uz.mediasolutions.barterlybackend.repository.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrencyRepository currencyRepository;
    private final RegionRepository regionRepository;
    private final LanguageRepository languageRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always")) {
            addRole();
            addAdmin();
            addCurrency();
            addLanguage();
            addRegion();
            addUserType();
        }

    }

    private void addUserType() {
        for (UserTypeEnum value : UserTypeEnum.values()) {
            userTypeRepository.save(new UserType(value));
        }
    }

    private void addLanguage() {
        Language language = Language.builder()
                .code("uz")
                .name("Uzbek")
                .build();
        languageRepository.save(language);
    }

    private void addRegion() {
        Region uzbekistan = Region.builder()
                .currency(currencyRepository.getReferenceById(1L))
                .build();
        regionRepository.save(uzbekistan);
    }

    private void addCurrency() {
        Currency uz = Currency.builder()
                .currencyCode("UZS")
                .build();
        currencyRepository.save(uz);
    }

    private void addRole() {
        for (RoleEnum value : RoleEnum.values()) {
            roleRepository.save(new Role(value));
        }
    }


    private void addAdmin() {
        User superAdmin = User.builder()
                .role(roleRepository.findByName(RoleEnum.ROLE_SUPER_ADMIN))
                .email("superadmin@gmail.com")
                .username("superadmin")
                .password(passwordEncoder.encode("Qwerty123@"))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();

        userRepository.save(superAdmin);
    }
}
