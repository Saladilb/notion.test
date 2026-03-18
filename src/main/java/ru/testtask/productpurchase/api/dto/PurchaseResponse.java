package ru.testtask.productpurchase.api.dto;

import java.math.BigDecimal;
import ru.testtask.productpurchase.purchase.PurchaseResult;

public record PurchaseResponse(
        String status,
        String paymentProcessor,
        Long product,
        String productName,
        BigDecimal finalPrice
) {

    public static PurchaseResponse from(PurchaseResult result) {
        return new PurchaseResponse(
                result.status(),
                result.paymentProcessor(),
                result.productId(),
                result.productName(),
                result.finalPrice()
        );
    }
}

