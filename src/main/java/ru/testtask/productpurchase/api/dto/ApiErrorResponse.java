package ru.testtask.productpurchase.api.dto;

import java.util.List;

public record ApiErrorResponse(
        String message,
        List<ApiFieldError> errors
) {
}

