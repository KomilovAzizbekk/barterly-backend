package uz.mediasolutions.barterlybackend.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.mediasolutions.barterlybackend.controller.abs.FileController;
import uz.mediasolutions.barterlybackend.service.abs.FileService;

@RestController
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {

    private final FileService fileService;

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadFile(String fileName) {
        return fileService.downloadFile(fileName);
    }

    @Override
    public ResponseEntity<String> deleteFile(String fileName) {
        return fileService.deleteFile(fileName);
    }
}
