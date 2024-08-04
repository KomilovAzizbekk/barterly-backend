package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
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
import uz.mediasolutions.barterlybackend.service.abs.UserService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<UserDTO> getMe() {
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

        Role role = roleRepository.findById(dto.getRoleId()).orElseThrow(
                () -> new RestException("Role does not exist", HttpStatus.NOT_FOUND)
        );

        if (role.getName().equals(RoleEnum.ROLE_USER)) {
            throw RestException.restThrow("Role type error (You chose user role)", HttpStatus.BAD_REQUEST);
        }

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
        Role role = roleRepository.findById(dto.getRoleId()).orElseThrow(
                () -> RestException.restThrow("Role not found", HttpStatus.NOT_FOUND)
        );
        User admin = userRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Admin not found", HttpStatus.NOT_FOUND)
        );

        if (admin.getRole().getName().equals(RoleEnum.ROLE_USER) || role.getName().equals(RoleEnum.ROLE_USER)) {
            throw RestException.restThrow("Role type error (You chose user role)", HttpStatus.BAD_REQUEST);
        }

        admin.setRole(role);
        admin.setUsername(dto.getUsername());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(admin);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }

    @Override
    public ResponseEntity<?> deleteAdmin(UUID id) {
        userRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Admin not found", HttpStatus.NOT_FOUND)
        );
        try {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }

}
