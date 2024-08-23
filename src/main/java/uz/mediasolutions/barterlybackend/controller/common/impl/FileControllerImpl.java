package uz.mediasolutions.barterlybackend.controller.common.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.mediasolutions.barterlybackend.controller.common.abs.FileController;
import uz.mediasolutions.barterlybackend.service.common.abs.FileService;

@RestController
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {

    private final FileService fileService;

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @Override
    public ResponseEntity<String> deleteFile(String fileName) {
        return fileService.deleteFile(fileName);
    }
}
