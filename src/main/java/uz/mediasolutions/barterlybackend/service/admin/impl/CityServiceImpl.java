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
import uz.mediasolutions.barterlybackend.entity.City;
import uz.mediasolutions.barterlybackend.entity.Region;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CityMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CityDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CityDTO2;
import uz.mediasolutions.barterlybackend.payload.request.CityReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CityResDTO;
import uz.mediasolutions.barterlybackend.repository.CityRepository;
import uz.mediasolutions.barterlybackend.repository.RegionRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.CityService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final RegionRepository regionRepository;

    private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long regionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CityDTO> cities = cityRepository.findAllCustom(lang, search, regionId, pageable);
        return ResponseEntity.ok(cities);
    }


    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        // ID orqali bazadan city'ni izlaymiz, agar yo'q bo'lsa 404 qaytaramiz
        City city = cityRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("City not Found", HttpStatus.NOT_FOUND)
        );

        // City'ga bog'liq regionni get qilib olamiz
        Region region = city.getRegion();

        // Response DTO yasab olamiz va 200 status bilan qaytaramiz
        CityResDTO dto = CityResDTO.builder()
                .id(city.getId())
                .names(city.getTranslations())
                .regionId(region.getId())
                .regionName(region.getTranslations().get(lang))
                .build();

        return ResponseEntity.ok(dto);
    }


    @Override
    public ResponseEntity<?> add(CityReqDTO dto) {
        City city = cityMapper.toEntity(dto);
        cityRepository.save(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }


    @Override
    public ResponseEntity<?> edit(CityReqDTO dto, Long id) {
        City city = cityRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("City not found", HttpStatus.NOT_FOUND)
        );

        Optional.ofNullable(dto.getTranslations()).ifPresent(city::setTranslations);
        Optional.ofNullable(dto.getRegionId()).ifPresent(regionId -> city.setRegion(
                regionRepository.findById(dto.getRegionId()).orElseThrow(
                        () -> RestException.restThrow("Region not found", HttpStatus.NOT_FOUND)
                )
        ));
        cityRepository.save(city);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }


    @Override
    public ResponseEntity<?> delete(Long id) {
        if (!cityRepository.existsById(id)) {
            throw RestException.restThrow("City not found", HttpStatus.NOT_FOUND);
        }

        try {
            cityRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }
}
