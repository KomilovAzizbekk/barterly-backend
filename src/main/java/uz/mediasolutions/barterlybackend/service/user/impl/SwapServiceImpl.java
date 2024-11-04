package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Item;
import uz.mediasolutions.barterlybackend.entity.Swap;
import uz.mediasolutions.barterlybackend.enums.SwapStatusEnum;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.SwapDTO;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;
import uz.mediasolutions.barterlybackend.repository.ItemRepository;
import uz.mediasolutions.barterlybackend.repository.SwapRepository;
import uz.mediasolutions.barterlybackend.service.user.abs.SwapService;

import java.util.*;

@Service
@AllArgsConstructor
public class SwapServiceImpl implements SwapService {

    private final SwapRepository swapRepository;
    private final ItemRepository itemRepository;

    @Override
    public Page<SwapDTO> getAll(String lang, UUID userId, int page, int size) {
        return swapRepository.findAllByUserId(userId, lang, PageRequest.of(page, size));
    }

    @Override
    public Swap create(SwapReqDTO dto) {
        // Ikkala Item ID ni bir so‘rovda olib kelamiz
        List<Item> items = itemRepository.findAllById(Arrays.asList(dto.getRequesterItemId(), dto.getResponderItemId()));

        // Ikkala Item mavjudligini tekshiramiz
        if (items.size() < 2) {
            throw RestException.restThrow("One or both items not found", HttpStatus.NOT_FOUND);
        }

        // Requester va Responder Item'larni ajratib olamiz
        Item requesterItem = items.stream()
                .filter(item -> item.getId().equals(dto.getRequesterItemId()))
                .findFirst()
                .orElseThrow(() -> RestException.restThrow("Requester item not found", HttpStatus.NOT_FOUND));

        Item responderItem = items.stream()
                .filter(item -> item.getId().equals(dto.getResponderItemId()))
                .findFirst()
                .orElseThrow(() -> RestException.restThrow("Responder item not found", HttpStatus.NOT_FOUND));

        // Item yoki User IDlari bir xil ekanligini tekshirish
        if (responderItem.getId().equals(requesterItem.getId()) ||
                responderItem.getUser().getId().equals(requesterItem.getUser().getId())) {
            throw RestException.restThrow("BAD REQUEST (Same Items or Users)", HttpStatus.BAD_REQUEST);
        }

        // Swap ob'ektini yaratish va saqlash
        Swap swap = Swap.builder()
                .message(dto.getMessage())
                .responderItemId(responderItem.getId())
                .requesterItemId(requesterItem.getId())
                .responderUserId(responderItem.getUser().getId())
                .requesterUserId(requesterItem.getUser().getId())
                .swapStatus(SwapStatusEnum.NEW)
                .build();

        return swapRepository.save(swap);
    }


    @Override
    public Swap accept(UUID swapId, boolean accept) {
        // Bazadan ID bo'yicha Swap'ni izlaymiz agar topilmasa 404 qaytaramiz
        Swap swap = swapRepository.findById(swapId).orElseThrow(
                () -> RestException.restThrow("Swap not found", HttpStatus.NOT_FOUND)
        );

        // true, false ga qarab status joylaymiz va qaytaramiz
        swap.setSwapStatus(accept ? SwapStatusEnum.ACCEPTED : SwapStatusEnum.REJECTED);
        return swapRepository.save(swap);
    }
}

