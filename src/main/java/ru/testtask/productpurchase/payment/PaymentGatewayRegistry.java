package ru.testtask.productpurchase.payment;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.testtask.productpurchase.common.error.BadRequestException;

@Component
public class PaymentGatewayRegistry {

    private final Map<String, PaymentGateway> gatewaysByCode;

    public PaymentGatewayRegistry(List<PaymentGateway> gateways) {
        this.gatewaysByCode = gateways.stream()
                .collect(Collectors.toUnmodifiableMap(
                        gateway -> gateway.getCode().toLowerCase(Locale.ROOT),
                        Function.identity()
                ));
    }

    public PaymentGateway getByCode(String code) {
        if (code == null || code.isBlank()) {
            throw new BadRequestException("Payment processor is required");
        }

        PaymentGateway gateway = gatewaysByCode.get(code.trim().toLowerCase(Locale.ROOT));
        if (gateway == null) {
            throw new BadRequestException("Unsupported payment processor: " + code);
        }

        return gateway;
    }
}

