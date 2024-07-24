package uz.mediasolutions.barterlybackend.mapper.impl;

import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.mapper.abs.CurrencyMapper;
import uz.mediasolutions.barterlybackend.payload.request.CurrencyReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CurrencyResDTO;

@Component
public class CurrencyMapperImpl implements CurrencyMapper {
    @Override
    public CurrencyResDTO toResDTO(Currency currency) {
        if (currency == null) {
            return null;
        }

        return CurrencyResDTO.builder()
                .id(currency.getId())
                .imageUrl(currency.getImageUrl())
                .currencyCode(currency.getCurrencyCode())
                .translations(currency.getTranslations())
                .build();
    }

    @Override
    public Currency toEntity(CurrencyReqDTO dto) {
        if (dto == null) {
            return null;
        }

        return Currency.builder()
                .currencyCode(dto.getCurrencyCode())
                .imageUrl(dto.getImageUrl())
                .translations(dto.getTranslations())
                .build();
    }
}
