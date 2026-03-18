package ru.testtask.productpurchase.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.testtask.productpurchase.api.validation.ValidTaxNumber;

public record PurchaseRequest(
        @NotNull(message = "product is required")
        Long product,

        @NotBlank(message = "taxNumber is required")
        @ValidTaxNumber
        String taxNumber,

        String couponCode,

        @NotBlank(message = "paymentProcessor is required")
        String paymentProcessor
) {
}

