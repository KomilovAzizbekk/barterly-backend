package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.UserMapper;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.request.AdminReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;
import uz.mediasolutions.barterlybackend.repository.RoleRepository;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.UserService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<UserDTO> getMe() {
        // Security Contextdan Authenticationni olamiz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw RestException.restThrow("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        if (authentication.getPrincipal() == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw RestException.restThrow("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userMapper.toDto(user));
    }


    @Override
    public ResponseEntity<Page<UserResDTO>> getAllUsers(String search, int page, int size) {
        Role role = roleRepository.findByName(RoleEnum.ROLE_USER);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResDTO> users = userRepository.findAllUsersCustom(role.getId(), search, pageable);
        return ResponseEntity.ok(users);
    }


    @Override
    public ResponseEntity<Page<AdminResDTO>> getAllAdmins(String search, int page, int size) {
        Role role = roleRepository.findByName(RoleEnum.ROLE_USER);
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminResDTO> admins = userRepository.findAllAdminsCustom(role.getId(), search, pageable);
        return ResponseEntity.ok(admins);
    }


    @Override
    public ResponseEntity<?> addAdmin(AdminReqDTO dto) {

        // dto'da kelayotgan role bazada mavjudligini tekshiramiz
        Role role = roleRepository.findById(dto.getRoleId()).orElseThrow(
                () -> new RestException("Role does not exist", HttpStatus.NOT_FOUND)
        );

        // Role USER bolsa 400 qaytaramiz
        if (role.getName().equals(RoleEnum.ROLE_USER)) {
            throw RestException.restThrow("Role type error (You chose user role)", HttpStatus.BAD_REQUEST);
        }

        // dto'dagi username bazada mavjud bo'lsa 409 qaytaramiz
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw RestException.restThrow("User already exists", HttpStatus.CONFLICT);
        }

        User admin = User.builder()
                .role(role)
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .credentialsNonExpired(true)
                .build();
        userRepository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }


    @Override
    public ResponseEntity<?> editAdmin(UUID id, AdminReqDTO dto) {
        User admin = userRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Admin not found", HttpStatus.NOT_FOUND)
        );

        // Agar tahrirlanayotgan adminning roli USER yoki SUPER_ADMIN bo'lsa 400
        if (admin.getRole().getName().equals(RoleEnum.ROLE_USER) || admin.getRole().getName().equals(RoleEnum.ROLE_SUPER_ADMIN)) {
            throw RestException.restThrow("Role type error", HttpStatus.BAD_REQUEST);
        }

        // Adminga birma-bir null bo'lmagan fieldlarni set qilib bazaga saqlaymiz
        Optional.ofNullable(dto.getUsername()).ifPresent(admin::setUsername);
        Optional.ofNullable(dto.getPassword()).ifPresent(password -> admin.setPassword(passwordEncoder.encode(password)));
        Optional.ofNullable(dto.getRoleId()).ifPresent(roleId -> admin.setRole(
                roleRepository.findById(roleId).orElseThrow(
                        () -> RestException.restThrow("Role not found", HttpStatus.NOT_FOUND)
                )
        ));
        userRepository.save(admin);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }


    @Override
    public ResponseEntity<?> deleteAdmin(UUID id) {
        // Ushbu ID ga tegishli user(admin) bor ekanligini tekshiramz, aks holda 404 qaytaramiz.
        User admin = userRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Admin not found", HttpStatus.NOT_FOUND)
        );
        // Agar SUPER_ADMIN ni delete qilishga harakat qilsa 403
        if (admin.getRole().getName().equals(RoleEnum.ROLE_SUPER_ADMIN)) {
            throw RestException.restThrow("Cannot delete SUPER_ADMIN", HttpStatus.FORBIDDEN);
        }
        try {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Rest.DELETED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }

}
