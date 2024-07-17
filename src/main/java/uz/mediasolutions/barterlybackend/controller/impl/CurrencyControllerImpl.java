package uz.mediasolutions.barterlybackend.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.abs.CurrencyController;
import uz.mediasolutions.barterlybackend.payload.request.CurrencyReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CurrencyResDTO;
import uz.mediasolutions.barterlybackend.service.abs.CurrencyService;

@RestController
@RequiredArgsConstructor
public class CurrencyControllerImpl implements CurrencyController {

    private final CurrencyService currencyService;

    @Override
    public ResponseEntity<Page<?>> getAll(String language, int page, int size) {
        return currencyService.getAll(language, page, size);
    }

    @Override
    public ResponseEntity<CurrencyResDTO> getById(Long id) {
        return currencyService.getById(id);
    }

    @Override
    public ResponseEntity<?> add(CurrencyReqDTO dto) {
        return currencyService.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CurrencyReqDTO dto) {
        return currencyService.edit(id, dto);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return currencyService.delete(id);
    }
}
