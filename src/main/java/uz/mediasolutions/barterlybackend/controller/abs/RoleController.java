package uz.mediasolutions.barterlybackend.controller.abs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.barterlybackend.payload.response.RoleResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.List;

@RequestMapping(Rest.BASE_PATH + "roles")
public interface RoleController {

    @GetMapping("/get-all")
    ResponseEntity<List<RoleResDTO>> getAllRoles();

}
