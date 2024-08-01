package uz.mediasolutions.barterlybackend.mapper.abs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.barterlybackend.entity.Neighborhood;
import uz.mediasolutions.barterlybackend.payload.request.NeighborhoodReqDTO;

@Mapper(componentModel = "spring")
public interface NeighborhoodMapper {

    @Mapping(target = "region.id", source = "regionId")
    @Mapping(target = "city.id", source = "cityId")
    Neighborhood toEntity(NeighborhoodReqDTO dto);

}
