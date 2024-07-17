package uz.mediasolutions.barterlybackend.mapper;

import org.mapstruct.Mapper;
import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.payload.request.CurrencyReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CurrencyResDTO;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyResDTO toResDTO(Currency currency);

    Currency toEntity(CurrencyReqDTO dto);

}
