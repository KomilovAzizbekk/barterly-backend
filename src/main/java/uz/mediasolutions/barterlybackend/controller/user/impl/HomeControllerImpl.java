package uz.mediasolutions.barterlybackend.controller.user.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.HomeController;
import uz.mediasolutions.barterlybackend.payload.response.HeaderResDTO;
import uz.mediasolutions.barterlybackend.service.user.abs.HomeService;

@RestController
@RequiredArgsConstructor
public class HomeControllerImpl implements HomeController {

    private final HomeService homeService;

    @Override
    public ResponseEntity<HeaderResDTO> getHeaderDetails(HttpServletRequest request, HttpSession session) {
        return homeService.getHeaderDetails(request, session);
    }

    @Override
    public ResponseEntity<Page<?>> getItems(String lang, int page, int size, HttpServletRequest request, HttpSession session) {
        return homeService.getItems(lang, page, size, request, session);
    }
}
