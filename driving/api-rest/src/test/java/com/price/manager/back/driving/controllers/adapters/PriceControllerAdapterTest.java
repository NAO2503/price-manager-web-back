package com.price.manager.back.driving.controllers.adapters;

import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.domain.Price;
import com.price.manager.back.driving.controllers.error.PriceNotFoundException;
import com.price.manager.back.driving.controllers.mappers.PriceMapper;
import com.price.manager.back.driving.controllers.models.PriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceControllerAdapterTest {

    @Mock
    private PriceServicePort priceServicePort;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceControllerAdapter priceRestController;


    @Test
    void returnPriceDataOK() {
        var dateTest = LocalDateTime.of(2020, 6, 14, 10, 0, 0)
                .atOffset(ZoneOffset.UTC);

        when(priceMapper.toResponseDto(any())).thenReturn(new PriceResponse());
        when(priceServicePort.findByBrandProductBetweenDate(anyString(), anyString(), anyString()))
                .thenReturn(Price.builder().startDate(LocalDateTime.now()).build());
        var result = priceRestController.findByBrandProductBetweenDate("2", "2", dateTest);
        assertNotNull(result);
    }

    @Test
    void returnNumberFormatException() {

        var dateTest = LocalDateTime.of(2020, 6, 14, 10, 0, 0)
                .atOffset(ZoneOffset.UTC);

        willThrow(new NumberFormatException("exception"))
                .given(priceServicePort).findByBrandProductBetweenDate(anyString(), anyString(),anyString());
        assertThrows(NumberFormatException.class, () -> {
            priceRestController.findByBrandProductBetweenDate("3A", "1", dateTest);
        });
    }

    @Test
    void returnResourceNotFoundException() {
        var dateTest = LocalDateTime.of(2020, 6, 14, 10, 0, 0)
                .atOffset(ZoneOffset.UTC);

        when(priceServicePort.findByBrandProductBetweenDate(anyString(), anyString(), anyString()))
                .thenReturn(null);
        assertThrows(PriceNotFoundException.class, () -> {
            priceRestController.findByBrandProductBetweenDate("2", "2", dateTest);
        });
    }
}
