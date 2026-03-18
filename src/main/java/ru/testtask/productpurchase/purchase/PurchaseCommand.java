package ru.testtask.productpurchase.purchase;

public record PurchaseCommand(
        Long productId,
        String taxNumber,
        String couponCode,
        String paymentProcessor
) {
}

