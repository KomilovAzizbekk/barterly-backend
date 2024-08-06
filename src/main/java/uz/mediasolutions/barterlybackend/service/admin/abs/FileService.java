package uz.mediasolutions.barterlybackend.service.admin.abs;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ResponseEntity<String> uploadFile(MultipartFile file);

    ResponseEntity<ByteArrayResource> downloadFile(String fileName);

    ResponseEntity<String> deleteFile(String fileName);
}
