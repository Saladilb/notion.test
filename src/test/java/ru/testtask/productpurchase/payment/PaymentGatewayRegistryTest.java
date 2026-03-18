package ru.testtask.productpurchase.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.testtask.productpurchase.common.error.BadRequestException;

class PaymentGatewayRegistryTest {

    @Test
    void shouldResolveGatewayIgnoringCase() {
        PaymentGatewayRegistry registry = new PaymentGatewayRegistry(List.of(new StubGateway("paypal")));

        PaymentGateway gateway = registry.getByCode("PayPal");

        assertThat(gateway.getCode()).isEqualTo("paypal");
    }

    @Test
    void shouldFailForUnsupportedGateway() {
        PaymentGatewayRegistry registry = new PaymentGatewayRegistry(List.of(new StubGateway("paypal")));

        assertThatThrownBy(() -> registry.getByCode("bank"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Unsupported payment processor: bank");
    }

    private record StubGateway(String code) implements PaymentGateway {

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public void pay(BigDecimal amount) {
        }
    }
}

