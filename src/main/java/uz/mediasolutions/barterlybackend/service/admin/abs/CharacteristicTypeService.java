package uz.mediasolutions.barterlybackend.service.admin.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicTypeDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicTypeReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO3;

import java.util.List;

public interface CharacteristicTypeService {

    Page<CharacteristicTypeDTO> getAll(String lang, String search, Long categoryId, int page, int size);

    CharacteristicTypeResDTO getById(String lang, Long id);

    String add(CharacteristicTypeReqDTO dto);

    String edit(Long id, CharacteristicTypeReqDTO dto);

    String delete(Long id);

    List<CharacteristicTypeResDTO3> getByCategoryId(String lang, Long categoryId);
}
