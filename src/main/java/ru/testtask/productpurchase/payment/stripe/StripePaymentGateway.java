package ru.testtask.productpurchase.payment.stripe;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testtask.productpurchase.payment.PaymentFailedException;
import ru.testtask.productpurchase.payment.PaymentGateway;

@Component
@RequiredArgsConstructor
public class StripePaymentGateway implements PaymentGateway {

    private final StripePaymentProcessor paymentProcessor;

    @Override
    public String getCode() {
        return "stripe";
    }

    @Override
    public void pay(BigDecimal amount) {
        boolean successful = paymentProcessor.pay(amount.floatValue());
        if (!successful) {
            throw new PaymentFailedException("Payment failed in stripe");
        }
    }
}
