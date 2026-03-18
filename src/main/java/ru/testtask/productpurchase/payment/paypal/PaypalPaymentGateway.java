package ru.testtask.productpurchase.payment.paypal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testtask.productpurchase.payment.PaymentFailedException;
import ru.testtask.productpurchase.payment.PaymentGateway;

@Component
@RequiredArgsConstructor
public class PaypalPaymentGateway implements PaymentGateway {

    private final PaypalPaymentProcessor paymentProcessor;

    @Override
    public String getCode() {
        return "paypal";
    }

    @Override
    public void pay(BigDecimal amount) {
        try {
            int normalizedAmount = amount.setScale(0, RoundingMode.HALF_UP).intValueExact();
            paymentProcessor.makePayment(normalizedAmount);
        } catch (Exception exception) {
            throw new PaymentFailedException("Payment failed in paypal");
        }
    }
}
