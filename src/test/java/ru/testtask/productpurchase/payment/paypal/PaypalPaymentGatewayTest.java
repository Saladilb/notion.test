package ru.testtask.productpurchase.payment.paypal;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PaypalPaymentGatewayTest {

    @Test
    void shouldRoundAmountBeforeDelegatingToProcessor() {
        TrackingPaypalPaymentProcessor paymentProcessor = new TrackingPaypalPaymentProcessor();
        PaypalPaymentGateway gateway = new PaypalPaymentGateway(paymentProcessor);

        gateway.pay(new BigDecimal("116.56"));

        assertThat(paymentProcessor.lastProcessedAmount).isEqualTo(117);
    }

    private static class TrackingPaypalPaymentProcessor extends PaypalPaymentProcessor {

        private Integer lastProcessedAmount;

        @Override
        public void makePayment(Integer price) {
            this.lastProcessedAmount = price;
        }
    }
}

