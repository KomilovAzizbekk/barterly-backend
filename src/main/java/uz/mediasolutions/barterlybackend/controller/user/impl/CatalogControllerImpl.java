package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.CatalogController;
import uz.mediasolutions.barterlybackend.service.user.abs.CatalogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogControllerImpl implements CatalogController {

    private final CatalogService service;

    @Override
    public ResponseEntity<List<?>> getParents(String lang) {
        return service.getParents(lang);
    }

    @Override
    public ResponseEntity<List<?>> getByParentId(String lang, Long parentId) {
        return service.getByParentId(lang, parentId);
    }
}
