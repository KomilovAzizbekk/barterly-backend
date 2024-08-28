package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Item;
import uz.mediasolutions.barterlybackend.entity.Swap;
import uz.mediasolutions.barterlybackend.enums.SwapStatusEnum;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.SwapDTO;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.SwapResDTO;
import uz.mediasolutions.barterlybackend.repository.ItemRepository;
import uz.mediasolutions.barterlybackend.repository.SwapRepository;
import uz.mediasolutions.barterlybackend.repository.SwapStatusRepository;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.service.user.abs.SwapService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class SwapServiceImpl implements SwapService {

    private final SwapRepository swapRepository;
    private final ItemRepository itemRepository;
    private final SwapStatusRepository swapStatusRepository;

    @Override
    public ResponseEntity<?> getAll(String lang, UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
//        if (Objects.equals(lang, "uz")) {
            int now = LocalDateTime.now().getSecond();
            Page<SwapDTO> swapDTOS = swapRepository.findAllByUserId(userId, lang, pageable);
            System.out.println(LocalDateTime.now().getSecond() - now);
            return ResponseEntity.ok(swapDTOS);
//        } else {
//            int now = LocalDateTime.now().getSecond();
//            Page<Swap> swaps = swapRepository.findAllByRequesterIdOrResponderId(userId, userId, pageable);
//            List<SwapResDTO> list = new ArrayList<>();
//            for (Swap swap : swaps) {
//                String title1, title2, username;
//                if (swap.getRequester().getId().equals(userId)) {
//                    Item item1 = itemRepository.findById(swap.getRequesterItem().getId()).orElse(null);
//                    Item item2 = itemRepository.findById(swap.getResponderItem().getId()).orElse(null);
//                    assert item1 != null;
//                    title1 = item1.getDescription();
//                    assert item2 != null;
//                    title2 = item2.getDescription();
//                    username = swap.getResponder().getUsername();
//                } else {
//                    Item item1 = itemRepository.findById(swap.getResponderItem().getId()).orElse(null);
//                    Item item2 = itemRepository.findById(swap.getRequesterItem().getId()).orElse(null);
//                    assert item1 != null;
//                    title1 = item1.getDescription();
//                    assert item2 != null;
//                    title2 = item2.getDescription();
//                    username = swap.getRequester().getUsername();
//                }
//
//                SwapResDTO swapResDTO = SwapResDTO.builder()
//                        .id(swap.getId())
//                        .title1(title1)
//                        .title2(title2)
//                        .username(username)
//                        .createdAt(swap.getCreatedAt().toString())
//                        .build();
//                list.add(swapResDTO);
//            }
//            Page<SwapResDTO> swapResDTOPage = new PageImpl<>(list, pageable, list.size());
//            System.out.println(LocalDateTime.now().getSecond() - now);
//            return ResponseEntity.ok(swapResDTOPage);
//        }
    }

    @Override
    public ResponseEntity<?> create(SwapReqDTO dto) {
        Item requesterItem = itemRepository.findById(dto.getRequesterItemId()).orElseThrow(
                () -> RestException.restThrow("Item not found", HttpStatus.NOT_FOUND)
        );

        Item responderItem = itemRepository.findById(dto.getResponderItemId()).orElseThrow(
                () -> RestException.restThrow("Item not found", HttpStatus.NOT_FOUND)
        );

        if (responderItem.getId() == requesterItem.getId() || responderItem.getUser().getId().equals(requesterItem.getUser().getId())) {
            throw RestException.restThrow("BAD REQUEST", HttpStatus.BAD_REQUEST);
        }
        Swap swap = Swap.builder()
                .message(dto.getMessage())
                .responderItem(responderItem)
                .requesterItem(requesterItem)
                .responder(responderItem.getUser())
                .requester(requesterItem.getUser())
                .swapStatus(swapStatusRepository.findByName(SwapStatusEnum.NEW))
                .build();
        swapRepository.save(swap);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }

    @Override
    public ResponseEntity<?> accept(UUID swapId, boolean accept) {
        Swap swap = swapRepository.findById(swapId).orElseThrow(
                () -> RestException.restThrow("Swap not found", HttpStatus.NOT_FOUND)
        );
        if (accept) {
            swap.setSwapStatus(swapStatusRepository.findByName(SwapStatusEnum.ACCEPTED));
        } else {
            swap.setSwapStatus(swapStatusRepository.findByName(SwapStatusEnum.REJECTED));
        }
        swapRepository.save(swap);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }
}

