package ru.testtask.productpurchase.payment;

import java.math.BigDecimal;

public interface PaymentGateway {

    String getCode();

    void pay(BigDecimal amount);
}

