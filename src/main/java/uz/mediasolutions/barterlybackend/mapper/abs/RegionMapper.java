package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.Region;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;

public interface RegionMapper {

    RegionResDTO toResDTO(Region region);

    Region toEntity(RegionReqDTO dto);

}
