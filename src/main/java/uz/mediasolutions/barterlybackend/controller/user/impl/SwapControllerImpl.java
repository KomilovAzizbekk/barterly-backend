package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.SwapController;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.SwapDTO;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;
import uz.mediasolutions.barterlybackend.service.user.abs.SwapService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SwapControllerImpl implements SwapController {

    private final SwapService swapService;

    @Override
    public ResponseEntity<Page<SwapDTO>> getAllByUserId(String lang, UUID userId, int page, int size) {
        return ResponseEntity.ok(swapService.getAll(lang, userId, page, size));
    }

    @Override
    public ResponseEntity<String> create(SwapReqDTO dto) {
        return ResponseEntity.status(201).body(swapService.create(dto));
    }

    @Override
    public ResponseEntity<String> accept(UUID swapId, boolean accept) {
        return ResponseEntity.status(202).body(swapService.accept(swapId, accept));
    }
}
