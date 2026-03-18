package ru.testtask.productpurchase.pricing;

import java.math.BigDecimal;

public record PriceBreakdown(
        Long productId,
        String productName,
        BigDecimal basePrice,
        BigDecimal discountAmount,
        BigDecimal subtotal,
        BigDecimal taxRate,
        BigDecimal taxAmount,
        BigDecimal finalPrice,
        String couponCode,
        String countryCode
) {
}

