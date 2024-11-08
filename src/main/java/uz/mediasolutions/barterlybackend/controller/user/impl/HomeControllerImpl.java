package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.HomeController;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.response.HeaderResDTO;
import uz.mediasolutions.barterlybackend.service.user.abs.HomeService;

@RestController
@RequiredArgsConstructor
public class HomeControllerImpl implements HomeController {

    private final HomeService homeService;

    @Override
    public ResponseEntity<HeaderResDTO> getHeaderDetails() {
        return ResponseEntity.ok(homeService.getHeaderDetails());
    }

    @Override
    public ResponseEntity<Page<ItemDTO>> search(String search, Long categoryId) {
        return ResponseEntity.ok(homeService.search(search, categoryId));
    }
}
