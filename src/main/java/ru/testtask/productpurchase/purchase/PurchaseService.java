package ru.testtask.productpurchase.purchase;

import org.springframework.stereotype.Service;
import ru.testtask.productpurchase.payment.PaymentGateway;
import ru.testtask.productpurchase.payment.PaymentGatewayRegistry;
import ru.testtask.productpurchase.pricing.PriceBreakdown;
import ru.testtask.productpurchase.pricing.PricingCommand;
import ru.testtask.productpurchase.pricing.PricingService;

@Service
public class PurchaseService {

    private final PricingService pricingService;
    private final PaymentGatewayRegistry paymentGatewayRegistry;

    public PurchaseService(PricingService pricingService, PaymentGatewayRegistry paymentGatewayRegistry) {
        this.pricingService = pricingService;
        this.paymentGatewayRegistry = paymentGatewayRegistry;
    }

    public PurchaseResult purchase(PurchaseCommand command) {
        PriceBreakdown priceBreakdown = pricingService.calculate(
                new PricingCommand(command.productId(), command.taxNumber(), command.couponCode())
        );

        PaymentGateway paymentGateway = paymentGatewayRegistry.getByCode(command.paymentProcessor());
        paymentGateway.pay(priceBreakdown.finalPrice());

        return new PurchaseResult(
                "SUCCESS",
                paymentGateway.getCode(),
                priceBreakdown.productId(),
                priceBreakdown.productName(),
                priceBreakdown.finalPrice()
        );
    }
}
