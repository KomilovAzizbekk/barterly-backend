package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.CharacteristicController;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.service.admin.abs.CharacteristicService;

@RestController
@RequiredArgsConstructor
public class CharacteristicControllerImpl implements CharacteristicController {

    private final CharacteristicService service;

    @Override
    public ResponseEntity<Page<CharacteristicDTO>> getAll(String lang, String search, Long categoryId, Long characteristicTypeId, int page, int size) {
        return ResponseEntity.ok(service.getAll(lang, search, categoryId, characteristicTypeId, page, size));
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        return ResponseEntity.ok(service.getById(lang, id));
    }

    @Override
    public ResponseEntity<?> add(CharacteristicReqDTO dto) {
        return ResponseEntity.status(201).body(service.add(dto));
    }

    @Override
    public ResponseEntity<?> edit(Long id, CharacteristicReqDTO dto) {
        return ResponseEntity.status(202).body(service.edit(id, dto));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return ResponseEntity.status(204).body(service.delete(id));
    }
}
