package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.RegionController;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;
import uz.mediasolutions.barterlybackend.service.admin.abs.RegionService;

@RestController
@RequiredArgsConstructor
public class RegionControllerImpl implements RegionController {

    private final RegionService regionService;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long currencyId, int page, int size) {
        return regionService.getAll(lang, search, currencyId, page, size);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        return regionService.getById(id);
    }

    @Override
    public ResponseEntity<?> add(RegionReqDTO dto) {
        return regionService.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, RegionReqDTO dto) {
        return regionService.edit(id, dto);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return regionService.delete(id);
    }
}
