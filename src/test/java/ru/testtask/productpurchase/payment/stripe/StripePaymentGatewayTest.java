package ru.testtask.productpurchase.payment.stripe;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import ru.testtask.productpurchase.payment.PaymentFailedException;

class StripePaymentGatewayTest {

    @Test
    void shouldThrowWhenProcessorRejectsPayment() {
        StripePaymentGateway gateway = new StripePaymentGateway(new StripePaymentProcessor());

        assertThatThrownBy(() -> gateway.pay(new BigDecimal("99.99")))
                .isInstanceOf(PaymentFailedException.class)
                .hasMessage("Payment failed in stripe");
    }
}
