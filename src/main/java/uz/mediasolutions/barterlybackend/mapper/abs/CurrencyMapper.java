package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.payload.request.CurrencyReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CurrencyResDTO;

public interface CurrencyMapper {

    CurrencyResDTO toResDTO(Currency currency);

    Currency toEntity(CurrencyReqDTO dto);

}
