package ru.testtask.productpurchase.api.dto;

import java.math.BigDecimal;
import ru.testtask.productpurchase.pricing.PriceBreakdown;

public record CalculatePriceResponse(
        Long product,
        String productName,
        BigDecimal basePrice,
        BigDecimal discountAmount,
        BigDecimal taxRate,
        BigDecimal taxAmount,
        BigDecimal finalPrice,
        String couponCode,
        String countryCode
) {

    public static CalculatePriceResponse from(PriceBreakdown breakdown) {
        return new CalculatePriceResponse(
                breakdown.productId(),
                breakdown.productName(),
                breakdown.basePrice(),
                breakdown.discountAmount(),
                breakdown.taxRate(),
                breakdown.taxAmount(),
                breakdown.finalPrice(),
                breakdown.couponCode(),
                breakdown.countryCode()
        );
    }
}

