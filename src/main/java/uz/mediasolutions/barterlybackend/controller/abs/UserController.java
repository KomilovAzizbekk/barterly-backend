package uz.mediasolutions.barterlybackend.controller.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.request.AdminReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.List;

@RequestMapping(Rest.BASE_PATH + "user")
public interface UserController {

    @GetMapping("/me")
    ResponseEntity<UserDTO> getMe();

    @GetMapping("/get-all-users")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<Page<UserResDTO>> getAllUsers(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get-all-admins")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<Page<AdminResDTO>> getAllAdmins(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping("add-admin")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> addAdmin(@Valid @RequestBody AdminReqDTO dto);

}
