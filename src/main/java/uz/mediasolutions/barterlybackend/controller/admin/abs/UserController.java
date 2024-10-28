package uz.mediasolutions.barterlybackend.controller.admin.abs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "admin/users")
public interface UserController {

    @GetMapping("/me")
    ResponseEntity<UserDTO> getMe();

    @GetMapping("/get-all-users")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResDTO.class))})
    })
    ResponseEntity<Page<UserResDTO>> getAllUsers(@RequestParam(required = false) String search,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);


    @GetMapping("/get-all-admins")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdminResDTO.class))})
    })
    ResponseEntity<Page<AdminResDTO>> getAllAdmins(@RequestParam(required = false) String search,
                                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);


    @PostMapping("/add-admin")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> addAdmin(@RequestBody @Valid AdminReqDTO dto);


    @PatchMapping("edit-admin/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> editAdmin(@PathVariable UUID id,
                                @RequestBody AdminReqDTO dto);


    @DeleteMapping("/delete-admin/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> deleteAdmin(@PathVariable UUID id);
}
