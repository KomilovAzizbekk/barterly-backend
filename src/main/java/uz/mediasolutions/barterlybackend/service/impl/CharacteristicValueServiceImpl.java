package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.entity.CharacteristicValue;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CharacteristicValueMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicValueDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicValueDTO2;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;
import uz.mediasolutions.barterlybackend.repository.CharacteristicRepository;
import uz.mediasolutions.barterlybackend.repository.CharacteristicValueRepository;
import uz.mediasolutions.barterlybackend.service.abs.CharacteristicValueService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@RequiredArgsConstructor
public class CharacteristicValueServiceImpl implements CharacteristicValueService {

    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicValueRepository characteristicValueRepository;
    private final CharacteristicValueMapper characteristicValueMapper;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long characteristicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CharacteristicValueDTO> dtos = characteristicValueRepository.findAllCustom(lang, search, characteristicId, pageable);
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        CharacteristicValueDTO2 characteristicValue = characteristicValueRepository.findByIdCustom(lang, id).orElseThrow(
                () -> RestException.restThrow("Characteristic value not found", HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(characteristicValue);
    }

    @Override
    public ResponseEntity<?> add(CharacteristicValueReqDTO dto) {
        CharacteristicValue entity = characteristicValueMapper.toEntity(dto);
        characteristicValueRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CharacteristicValueReqDTO dto) {
        CharacteristicValue characteristicValue = characteristicValueRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic value not found", HttpStatus.NOT_FOUND)
        );
        Characteristic characteristic = characteristicRepository.findById(dto.getCharacteristicId()).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
        );

        characteristicValue.setTranslations(dto.getTranslations());
        characteristicValue.setCharacteristic(characteristic);
        characteristicValueRepository.save(characteristicValue);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        characteristicValueRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic value not found", HttpStatus.NOT_FOUND)
        );
        try {
            characteristicValueRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
