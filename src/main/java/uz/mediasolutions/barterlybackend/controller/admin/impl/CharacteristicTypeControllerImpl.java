package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.CharacteristicTypeController;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicTypeDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicTypeReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO3;
import uz.mediasolutions.barterlybackend.service.admin.abs.CharacteristicTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CharacteristicTypeControllerImpl implements CharacteristicTypeController {

    private final CharacteristicTypeService service;

    @Override
    public ResponseEntity<Page<CharacteristicTypeDTO>> getAll(String lang, String search, Long categoryId, int page, int size) {
        return ResponseEntity.ok(service.getAll(lang, search, categoryId, page, size));
    }

    @Override
    public ResponseEntity<CharacteristicTypeResDTO> getById(String lang, Long id) {
        return ResponseEntity.ok(service.getById(lang, id));
    }

    @Override
    public ResponseEntity<List<CharacteristicTypeResDTO3>> getByCategoryId(String lang, Long categoryId) {
        return ResponseEntity.ok(service.getByCategoryId(lang, categoryId));
    }

    @Override
    public ResponseEntity<String> add(CharacteristicTypeReqDTO dto) {
        return ResponseEntity.status(201).body(service.add(dto));
    }

    @Override
    public ResponseEntity<String> edit(Long id, CharacteristicTypeReqDTO dto) {
        return ResponseEntity.status(202).body(service.edit(id, dto));
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return ResponseEntity.status(204).body(service.delete(id));
    }
}
