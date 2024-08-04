package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
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
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.NeighborhoodDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.NeighborhoodDTO2;
import uz.mediasolutions.barterlybackend.payload.request.NeighborhoodReqDTO;
import uz.mediasolutions.barterlybackend.repository.CityRepository;
import uz.mediasolutions.barterlybackend.repository.NeighborhoodRepository;
import uz.mediasolutions.barterlybackend.repository.RegionRepository;
import uz.mediasolutions.barterlybackend.service.abs.NeighborhoodService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@RequiredArgsConstructor
public class NeighborhoodServiceImpl implements NeighborhoodService {

    private final NeighborhoodRepository neighborhoodRepository;
    private final NeighborhoodMapper neighborhoodMapper;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long regionId, Long cityId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NeighborhoodDTO> neighborhoodDTOS = neighborhoodRepository.findAllCustom(lang, search, regionId, cityId, pageable);
        return ResponseEntity.ok(neighborhoodDTOS);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        NeighborhoodDTO2 neighborhoodDTO2 = neighborhoodRepository.findByIdCustom(lang, id).orElseThrow(
                () -> RestException.restThrow("Neighborhood not found", HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(neighborhoodDTO2);
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

        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow(
                () -> RestException.restThrow("Region not found", HttpStatus.NOT_FOUND)
        );

        City city = cityRepository.findById(dto.getCityId()).orElseThrow(
                () -> RestException.restThrow("City not found", HttpStatus.NOT_FOUND)
        );

        neighborhood.setTranslations(dto.getTranslations());
        neighborhood.setRegion(region);
        neighborhood.setCity(city);
        neighborhoodRepository.save(neighborhood);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        neighborhoodRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Neighborhood not found", HttpStatus.NOT_FOUND)
        );
        try {
            neighborhoodRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }
}
