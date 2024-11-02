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
import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.entity.Region;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.RegionMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.RegionDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.RegionDTO2;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.RegionResDTO;
import uz.mediasolutions.barterlybackend.repository.CurrencyRepository;
import uz.mediasolutions.barterlybackend.repository.RegionRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.RegionService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    private final CurrencyRepository currencyRepository;

    private static final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long currencyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegionDTO> regionDTOS = regionRepository.findAllCustom(lang, search, currencyId, pageable);
        return ResponseEntity.ok(regionDTOS);
    }


    @Override
    public ResponseEntity<?> getById(Long id) {
        // ID orqali bazadan Region'ni izlaymiz, agar topilmasa 404 qaytaramiz
        Region region = regionRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Region not Found", HttpStatus.NOT_FOUND)
        );

        // Region'dan Currency'ni get qilib olamiz
        Currency currency = region.getCurrency();

        // Response DTO yasab olamiz va 200 status bilan qaytaramiz
        RegionResDTO dto = RegionResDTO.builder()
                .id(region.getId())
                .names(region.getTranslations())
                .imageUrl(region.getImageUrl())
                .currencyCode(currency.getCurrencyCode())
                .currencyId(currency.getId())
                .build();

        return ResponseEntity.ok(dto);
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

        // Agar dto ichidagi fieldlar null bolmasa bazaga saqlanadi, aks holda yo'q
        Optional.ofNullable(dto.getImageUrl()).ifPresent(region::setImageUrl);
        Optional.ofNullable(dto.getTranslations()).ifPresent(region::setTranslations);
        Optional.ofNullable(dto.getCurrencyId()).ifPresent(currencyId -> region.setCurrency(
                currencyRepository.findById(currencyId).orElseThrow(
                        () -> RestException.restThrow("Currency not found", HttpStatus.NOT_FOUND)
                )
        ));
        regionRepository.save(region);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }


    @Override
    public ResponseEntity<?> delete(Long id) {
        // Agar ushbu id'lik region topilmasa 404 qaytaramiz
        if (!regionRepository.existsById(id)) {
            throw new RestException("Region not found", HttpStatus.NOT_FOUND);
        }

        try {
            regionRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Rest.DELETED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }
}
