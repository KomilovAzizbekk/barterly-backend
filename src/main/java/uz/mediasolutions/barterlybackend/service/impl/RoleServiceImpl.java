package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.payload.response.RoleResDTO;
import uz.mediasolutions.barterlybackend.repository.RoleRepository;
import uz.mediasolutions.barterlybackend.service.abs.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<List<RoleResDTO>> findAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResDTO> roleResDTOs = new ArrayList<>();
        for (Role role : roles) {
            RoleResDTO roleResDTO = new RoleResDTO(role.getId(), role.getName().name());
            roleResDTOs.add(roleResDTO);
        }
        return ResponseEntity.ok(roleResDTOs);
    }
}
