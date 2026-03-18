package ru.testtask.productpurchase.payment.stripe;

import org.springframework.stereotype.Component;

@Component
public class StripePaymentProcessor {

    public boolean pay(Float price) {
        return price >= 100;
    }
}

