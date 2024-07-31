package uz.mediasolutions.barterlybackend.controller.admin.abs;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "files")
public interface FileController {

    @PostMapping("/upload")
    ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file);

    @GetMapping("/download/{fileName}")
    ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileName") String fileName);

    @DeleteMapping("/delete/{fileName}")
    ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName);


}
