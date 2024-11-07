package uz.mediasolutions.barterlybackend.service.admin.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;

public interface CharacteristicService {

    Page<CharacteristicDTO> getAll(String lang, String search, Long categoryId, Long characteristicTypeId, int page, int size);

    CharacteristicResDTO getById(String lang, Long id);

    String add(CharacteristicReqDTO dto);

    String edit(Long id, CharacteristicReqDTO dto);

    String delete(Long id);
}
