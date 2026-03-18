package ru.testtask.productpurchase.payment;

import ru.testtask.productpurchase.common.error.BadRequestException;

public class PaymentFailedException extends BadRequestException {

    public PaymentFailedException(String message) {
        super(message);
    }
}

