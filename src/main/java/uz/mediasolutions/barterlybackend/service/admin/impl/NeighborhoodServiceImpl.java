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
import uz.mediasolutions.barterlybackend.entity.Neighborhood;
import uz.mediasolutions.barterlybackend.entity.Region;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.NeighborhoodMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.NeighborhoodDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.NeighborhoodDTO2;
import uz.mediasolutions.barterlybackend.payload.request.NeighborhoodReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.NeighborhoodResDTO;
import uz.mediasolutions.barterlybackend.repository.CityRepository;
import uz.mediasolutions.barterlybackend.repository.NeighborhoodRepository;
import uz.mediasolutions.barterlybackend.repository.RegionRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.NeighborhoodService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NeighborhoodServiceImpl implements NeighborhoodService {

    private final NeighborhoodRepository neighborhoodRepository;
    private final NeighborhoodMapper neighborhoodMapper;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;

    private static final Logger log = LoggerFactory.getLogger(NeighborhoodServiceImpl.class);

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long regionId, Long cityId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NeighborhoodDTO> neighborhoodDTOS = neighborhoodRepository.findAllCustom(lang, search, regionId, cityId, pageable);
        return ResponseEntity.ok(neighborhoodDTOS);
    }


    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        // ID bo'yicha Neighborhood'ni bazadan izlaymiz, agar topilmasa 404 qaytaramiz
        Neighborhood neighborhood = neighborhoodRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Neighborhood not found", HttpStatus.NOT_FOUND)
        );

        // Neighborhood'ning City va Region'larini get qilib olamiz
        City city = neighborhood.getCity();

        Region region = neighborhood.getRegion();

        // Response DTO yasaymiz va 200 status bilan qaytaramiz
        NeighborhoodResDTO dto = NeighborhoodResDTO.builder()
                .id(neighborhood.getId())
                .names(neighborhood.getTranslations())
                .cityId(city.getId())
                .cityName(city.getTranslations().get(lang))
                .regionId(region.getId())
                .regionName(region.getTranslations().get(lang))
                .build();

        return ResponseEntity.ok(dto);
    }


    @Override
    public ResponseEntity<?> add(NeighborhoodReqDTO dto) {
        Neighborhood entity = neighborhoodMapper.toEntity(dto);
        neighborhoodRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }


    @Override
    public ResponseEntity<?> edit(Long id, NeighborhoodReqDTO dto) {
        Neighborhood neighborhood = neighborhoodRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Neighborhood not found", HttpStatus.NOT_FOUND)
        );

        // Null bo'lmagan barcha fieldlarni set qilamz va bazaga qo'shamiz
        Optional.ofNullable(dto.getTranslations()).ifPresent(neighborhood::setTranslations);
        Optional.ofNullable(dto.getRegionId()).ifPresent(regionId -> neighborhood.setRegion(
                regionRepository.findById(regionId).orElseThrow(
                        () -> RestException.restThrow("Region not found", HttpStatus.NOT_FOUND)
                )
        ));
        Optional.ofNullable(dto.getCityId()).ifPresent(cityId -> neighborhood.setCity(
                cityRepository.findById(cityId).orElseThrow(
                        () -> RestException.restThrow("City not found", HttpStatus.NOT_FOUND)
                )
        ));
        neighborhoodRepository.save(neighborhood);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }


    @Override
    public ResponseEntity<?> delete(Long id) {
        // Agar bazada ushbu id'lik Neighborhood topilmasa 404 qaytaramiz
        if (!neighborhoodRepository.existsById(id)) {
            throw RestException.restThrow("Neighborhood not found", HttpStatus.NOT_FOUND);
        }

        try {
            neighborhoodRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }
}
