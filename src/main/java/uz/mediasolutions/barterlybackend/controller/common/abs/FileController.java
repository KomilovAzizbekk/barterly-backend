package uz.mediasolutions.barterlybackend.controller.common.abs;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "files")
public interface FileController {

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file);

    @DeleteMapping("/delete/{fileName}")
    ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName);

}
