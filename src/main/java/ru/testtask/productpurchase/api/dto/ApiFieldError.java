package ru.testtask.productpurchase.api.dto;

public record ApiFieldError(
        String field,
        String message
) {
}

