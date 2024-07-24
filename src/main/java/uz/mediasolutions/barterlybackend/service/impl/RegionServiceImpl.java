package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.RegionDTO;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;
import uz.mediasolutions.barterlybackend.repository.RegionRepository;
import uz.mediasolutions.barterlybackend.service.abs.RegionService;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public ResponseEntity<Page<?>> getAll(String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegionDTO> allByOrderByNameAsc = regionRepository.findAllByOrderByNameAsc(language, pageable);
        return ResponseEntity.ok(allByOrderByNameAsc);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> add(RegionReqDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<?> edit(Long id, RegionReqDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
