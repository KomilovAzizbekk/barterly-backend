package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.entity.Region;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.RegionMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.RegionDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.RegionDTO2;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;
import uz.mediasolutions.barterlybackend.repository.CurrencyRepository;
import uz.mediasolutions.barterlybackend.repository.RegionRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.RegionService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    private final CurrencyRepository currencyRepository;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long currencyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegionDTO> regionDTOS = regionRepository.findAllCustom(lang, search, currencyId, pageable);
        return ResponseEntity.ok(regionDTOS);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        RegionDTO2 region = regionRepository.findByIdCustom(id).orElseThrow(
                () -> RestException.restThrow("Region not found", HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(region);
    }

    @Override
    public ResponseEntity<?> add(RegionReqDTO dto) {
        Region region = regionMapper.toEntity(dto);
        regionRepository.save(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }

    @Override
    public ResponseEntity<?> edit(Long id, RegionReqDTO dto) {
        Region region = regionRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Region not found", HttpStatus.NOT_FOUND)
        );

        Currency currency = currencyRepository.findById(dto.getCurrencyId()).orElseThrow(
                () -> RestException.restThrow("Currency not found", HttpStatus.NOT_FOUND)
        );
        region.setTranslations(dto.getTranslations());
        region.setCurrency(currency);
        region.setImageUrl(dto.getImageUrl());
        regionRepository.save(region);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        regionRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Region not found", HttpStatus.NOT_FOUND)
        );
        try {
            regionRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }
}