package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.mapper.abs.RoleMapper;
import uz.mediasolutions.barterlybackend.payload.response.RoleResDTO;
import uz.mediasolutions.barterlybackend.repository.RoleRepository;
import uz.mediasolutions.barterlybackend.service.abs.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public ResponseEntity<List<RoleResDTO>> findAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResDTO> list = roles.stream().map(roleMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<List<RoleResDTO>> getAllAdminRoles() {
        List<Role> roles = roleRepository.findAllByNameIsNot(RoleEnum.ROLE_USER);
        List<RoleResDTO> list = roles.stream().map(roleMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
