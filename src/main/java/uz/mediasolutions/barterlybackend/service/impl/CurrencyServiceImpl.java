package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.CurrencyMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CurrencyDTO;
import uz.mediasolutions.barterlybackend.payload.request.CurrencyReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CurrencyResDTO;
import uz.mediasolutions.barterlybackend.repository.CurrencyRepository;
import uz.mediasolutions.barterlybackend.service.abs.CurrencyService;
import uz.mediasolutions.barterlybackend.service.abs.FileService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final FileService fileService;

    @Override
    public ResponseEntity<Page<?>> getAll(String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CurrencyDTO> currencyDTOS = currencyRepository.findAllByOrderByCreatedAtDesc(language, pageable);
        return ResponseEntity.ok(currencyDTOS);
    }

    @Override
    public ResponseEntity<CurrencyResDTO> getById(Long id) {
        Currency currency = currencyRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Currency not found", HttpStatus.BAD_REQUEST)
        );
        CurrencyResDTO resDTO = currencyMapper.toResDTO(currency);
        return ResponseEntity.ok(resDTO);
    }

    @Override
    public ResponseEntity<?> add(CurrencyReqDTO dto) {
        try {
            Currency entity = currencyMapper.toEntity(dto);
            currencyRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Error with adding", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> edit(Long id, CurrencyReqDTO dto) {
        Currency currency = currencyRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Currency not found", HttpStatus.BAD_REQUEST)
        );

        if (!Objects.equals(currency.getImageUrl(), dto.getImageUrl())) {
            fileService.deleteFile(currency.getImageUrl());
        }

        try {
            currency.setCurrencyCode(dto.getCurrencyCode());
            currency.setTranslations(dto.getTranslations());
            currency.setImageUrl(dto.getImageUrl());
            currencyRepository.save(currency);
            return ResponseEntity.ok("Edited successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Error with editing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Currency currency = currencyRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Currency not found", HttpStatus.BAD_REQUEST)
        );
        try {
            fileService.deleteFile(currency.getImageUrl());

            currencyRepository.deleteById(id);
            return ResponseEntity.ok("Delete successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Error with deleting", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
