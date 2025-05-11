package com.price.manager.back.driving.controllers.mappers;

import com.price.manager.back.domain.Price;
import com.price.manager.back.driving.controllers.models.PriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(target = "id", source = "priceList")
    @Mapping(target = "startDate", expression = "java(toUtcOffsetDateTime(price.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(toUtcOffsetDateTime(price.getEndDate()))")
    PriceResponse toResponseDto(Price price);

    default OffsetDateTime toUtcOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atOffset(ZoneOffset.UTC);
    }
}