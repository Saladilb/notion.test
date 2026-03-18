package ru.testtask.productpurchase.payment.paypal;

import org.springframework.stereotype.Component;

@Component
public class PaypalPaymentProcessor {

    public void makePayment(Integer price) throws Exception {
        if (price > 100000) {
            throw new Exception("Paypal amount limit exceeded");
        }
    }
}

