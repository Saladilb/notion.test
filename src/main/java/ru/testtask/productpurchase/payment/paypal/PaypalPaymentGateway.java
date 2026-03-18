package ru.testtask.productpurchase.payment.paypal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;
import ru.testtask.productpurchase.payment.PaymentFailedException;
import ru.testtask.productpurchase.payment.PaymentGateway;

@Component
public class PaypalPaymentGateway implements PaymentGateway {

    private final PaypalPaymentProcessor paymentProcessor;

    public PaypalPaymentGateway(PaypalPaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public String getCode() {
        return "paypal";
    }

    @Override
    public void pay(BigDecimal amount) {
        try {
            // The mock Paypal contract accepts only an integer amount.
            int normalizedAmount = amount.setScale(0, RoundingMode.HALF_UP).intValueExact();
            paymentProcessor.makePayment(normalizedAmount);
        } catch (Exception exception) {
            throw new PaymentFailedException("Payment failed in paypal");
        }
    }
}

