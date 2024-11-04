package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.barterlybackend.entity.Swap;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.SwapDTO;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;

import java.util.UUID;

public interface SwapService {

    Page<SwapDTO> getAll(String lang, UUID userId, int page, int size);

    Swap create(SwapReqDTO dto);

    Swap accept(UUID swapId, boolean accept);
}
