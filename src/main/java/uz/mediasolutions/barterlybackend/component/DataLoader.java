package uz.mediasolutions.barterlybackend.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.*;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.enums.SwapStatusEnum;
import uz.mediasolutions.barterlybackend.enums.UserTypeEnum;
import uz.mediasolutions.barterlybackend.repository.*;

import java.util.HashMap;
import java.util.Map;

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
    private final SwapStatusRepository swapStatusRepository;

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
            addSwapStatuses();
        }
    }

    private void addSwapStatuses() {
        for (SwapStatusEnum value : SwapStatusEnum.values()) {
            swapStatusRepository.save(new SwapStatus(value));
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
        Map<String, String> translations = new HashMap<>();
        translations.put("uz", "Uzbekistan");
        Region uzbekistan = Region.builder()
                .currency(currencyRepository.getReferenceById(1L))
                .translations(translations)
                .build();
        regionRepository.save(uzbekistan);
    }

    private void addCurrency() {
        Map<String, String> translations = new HashMap<>();
        translations.put("uz", "Uzbekistan somi");
        Currency uz = Currency.builder()
                .currencyCode("UZS")
                .translations(translations)
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
                .username("barterly")
                .password(passwordEncoder.encode("Qwerty123@"))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();

        userRepository.save(superAdmin);
    }
}
