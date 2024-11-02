package uz.mediasolutions.barterlybackend.mapper.abs;

import org.mapstruct.Mapping;
import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.payload.request.CurrencyReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CurrencyResDTO;

public interface CurrencyMapper {

    @Mapping(source = "translations", target = "names")
    CurrencyResDTO toResDTO(Currency currency);

    Currency toEntity(CurrencyReqDTO dto);

}
