package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.SwapDTO;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;

import java.util.UUID;

public interface SwapService {

    Page<SwapDTO> getAll(String lang, UUID userId, int page, int size);

    Page<SwapDTO> getAllMine(String lang, String status, int page, int size);

    String create(SwapReqDTO dto);

    String accept(UUID swapId, boolean accept);
}
