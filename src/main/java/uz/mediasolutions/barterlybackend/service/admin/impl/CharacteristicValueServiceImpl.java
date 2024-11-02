package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CharacteristicValueDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CharacteristicValueDTO2;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;
import uz.mediasolutions.barterlybackend.repository.CharacteristicRepository;
import uz.mediasolutions.barterlybackend.repository.CharacteristicValueRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.CharacteristicValueService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacteristicValueServiceImpl implements CharacteristicValueService {

    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicValueRepository characteristicValueRepository;
    private final CharacteristicValueMapper characteristicValueMapper;

    private static final Logger log = LoggerFactory.getLogger(CharacteristicValueServiceImpl.class);

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long characteristicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CharacteristicValueDTO> dtos = characteristicValueRepository.findAllCustom(lang, search, characteristicId, pageable);
        return ResponseEntity.ok(dtos);
    }


    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        // ID orqali Characteristic Value'ni bazadan izlaymiz, agar topilmasa 404 qaytaramiz
        CharacteristicValue characteristicValue = characteristicValueRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic value not found", HttpStatus.NOT_FOUND)
        );

        // Characteristic Value'ga tegishli Characteristic'ni get qilib olamiz
        Characteristic characteristic = characteristicValue.getCharacteristic();

        // Response DTO yaratamiz va 200 status bilan qaytaramiz
        CharacteristicValueResDTO dto = characteristicValueMapper.toResDTO(characteristicValue, characteristic, lang);

        return ResponseEntity.ok(dto);
    }


    @Override
    public ResponseEntity<?> add(CharacteristicValueReqDTO dto) {
        CharacteristicValue entity = characteristicValueMapper.toEntity(dto);
        characteristicValueRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }


    @Override
    public ResponseEntity<?> edit(Long id, CharacteristicValueReqDTO dto) {
        // ID orqali Characteristic Value'ni bazadan izlaymiz, agar topilmasa 404 qaytaramiz
        CharacteristicValue characteristicValue = characteristicValueRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic value not found", HttpStatus.NOT_FOUND)
        );

        // Null bo'lmagan fieldlarni entity'ga set qilamz va bazaga saqlaymiz
        Optional.ofNullable(dto.getTranslations()).ifPresent(characteristicValue::setTranslations);
        Optional.ofNullable(dto.getCharacteristicId()).ifPresent(characteristicId -> characteristicValue.setCharacteristic(
                characteristicRepository.findById(characteristicId).orElseThrow(
                        () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
                )
        ));
        characteristicValueRepository.save(characteristicValue);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }


    @Override
    public ResponseEntity<?> delete(Long id) {
        if (!characteristicValueRepository.existsById(id)) {
            throw RestException.restThrow("Characteristic value not found", HttpStatus.NOT_FOUND);
        }

        try {
            characteristicValueRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
