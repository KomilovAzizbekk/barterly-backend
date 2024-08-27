package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.SwapController;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;
import uz.mediasolutions.barterlybackend.service.user.abs.SwapService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SwapControllerImpl implements SwapController {

    private final SwapService swapService;

    @Override
    public ResponseEntity<?> getAllByUserId(String lang, UUID userId, int page, int size) {
        return swapService.getAll(lang, userId, page, size);
    }

    @Override
    public ResponseEntity<?> create(SwapReqDTO dto) {
        return swapService.create(dto);
    }

    @Override
    public ResponseEntity<?> accept(UUID swapId, boolean accept) {
        return swapService.accept(swapId, accept);
    }
}
