package uz.mediasolutions.barterlybackend.mapper.abs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.barterlybackend.entity.Region;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    @Mapping(target = "currency.id", source = "currencyId")
    Region toEntity(RegionReqDTO dto);

}
