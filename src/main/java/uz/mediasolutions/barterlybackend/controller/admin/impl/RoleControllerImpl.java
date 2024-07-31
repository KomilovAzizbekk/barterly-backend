package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.RoleController;
import uz.mediasolutions.barterlybackend.payload.response.RoleResDTO;
import uz.mediasolutions.barterlybackend.service.abs.RoleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;

    @Override
    public ResponseEntity<List<RoleResDTO>> getAllRoles() {
        return roleService.findAll();
    }

    @Override
    public ResponseEntity<List<RoleResDTO>> getAllAdminRoles() {
        return roleService.getAllAdminRoles();
    }
}
