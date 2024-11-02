package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.CityController;
import uz.mediasolutions.barterlybackend.payload.request.CityReqDTO;
import uz.mediasolutions.barterlybackend.service.admin.abs.CityService;

@RestController
@RequiredArgsConstructor
public class CityControllerImpl implements CityController {

    private final CityService cityService;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long regionId, int page, int size) {
        return cityService.getAll(lang, search, regionId, page, size);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        return cityService.getById(lang, id);
    }

    @Override
    public ResponseEntity<?> add(CityReqDTO dto) {
        return cityService.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CityReqDTO dto) {
        return cityService.edit(dto, id);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return cityService.delete(id);
    }
}
