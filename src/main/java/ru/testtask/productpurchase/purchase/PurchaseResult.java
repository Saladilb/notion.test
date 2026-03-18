package ru.testtask.productpurchase.purchase;

import java.math.BigDecimal;

public record PurchaseResult(
        String status,
        String paymentProcessor,
        Long productId,
        String productName,
        BigDecimal finalPrice
) {
}

