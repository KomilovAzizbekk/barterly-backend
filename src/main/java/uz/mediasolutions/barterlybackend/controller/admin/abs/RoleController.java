package uz.mediasolutions.barterlybackend.controller.admin.abs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.barterlybackend.payload.response.RoleResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.List;

@RequestMapping(Rest.BASE_PATH + "admin/roles")
public interface RoleController {

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<List<RoleResDTO>> getAllRoles();

    @GetMapping("/get-all-admin-roles")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<List<RoleResDTO>> getAllAdminRoles();

}
