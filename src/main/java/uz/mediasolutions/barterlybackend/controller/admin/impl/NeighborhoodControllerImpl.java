package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.NeighborhoodController;
import uz.mediasolutions.barterlybackend.payload.request.NeighborhoodReqDTO;
import uz.mediasolutions.barterlybackend.service.abs.NeighborhoodService;

@RestController
@RequiredArgsConstructor
public class NeighborhoodControllerImpl implements NeighborhoodController {

    private final NeighborhoodService service;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long regionId, Long cityId, int page, int size) {
        return service.getAll(lang, search, regionId, cityId, page, size);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        return service.getById(lang, id);
    }

    @Override
    public ResponseEntity<?> add(NeighborhoodReqDTO dto) {
        return service.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, NeighborhoodReqDTO dto) {
        return service.edit(id, dto);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return service.delete(id);
    }
}
