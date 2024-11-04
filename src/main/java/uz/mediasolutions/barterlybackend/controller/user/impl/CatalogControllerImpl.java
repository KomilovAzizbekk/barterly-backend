package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.CatalogController;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.CatalogDTO;
import uz.mediasolutions.barterlybackend.payload.response.CatalogResDTO;
import uz.mediasolutions.barterlybackend.service.user.abs.CatalogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogControllerImpl implements CatalogController {

    private final CatalogService service;

    @Override
    public ResponseEntity<List<CatalogDTO>> getParents(String lang) {
        return ResponseEntity.ok(service.getParents(lang));
    }

    @Override
    public ResponseEntity<List<CatalogDTO>> getByParentId(String lang, Long parentId) {
        return ResponseEntity.ok(service.getByParentId(lang, parentId));
    }

    @Override
    public ResponseEntity<List<CatalogResDTO>> getAllByParentId(String lang, Long parentId) {
        return ResponseEntity.ok(service.getAllByParentId(lang, parentId));
    }
}
