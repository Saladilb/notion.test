package ru.testtask.productpurchase.pricing;

public record PricingCommand(
        Long productId,
        String taxNumber,
        String couponCode
) {
}

