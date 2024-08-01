package uz.mediasolutions.barterlybackend.mapper.abs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.barterlybackend.entity.City;
import uz.mediasolutions.barterlybackend.payload.request.CityReqDTO;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "region.id", source = "regionId")
    City toEntity(CityReqDTO dto);

}
