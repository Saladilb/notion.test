package ru.testtask.productpurchase.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.testtask.productpurchase.api.dto.CalculatePriceRequest;
import ru.testtask.productpurchase.api.dto.CalculatePriceResponse;
import ru.testtask.productpurchase.api.dto.PurchaseRequest;
import ru.testtask.productpurchase.api.dto.PurchaseResponse;
import ru.testtask.productpurchase.pricing.PriceBreakdown;
import ru.testtask.productpurchase.pricing.PricingCommand;
import ru.testtask.productpurchase.pricing.PricingService;
import ru.testtask.productpurchase.purchase.PurchaseCommand;
import ru.testtask.productpurchase.purchase.PurchaseResult;
import ru.testtask.productpurchase.purchase.PurchaseService;

@RestController
@RequiredArgsConstructor
public class ProductPurchaseController {

    private final PricingService pricingService;
    private final PurchaseService purchaseService;

    @PostMapping("/calculate-price")
    public CalculatePriceResponse calculatePrice(@Valid @RequestBody CalculatePriceRequest request) {
        PriceBreakdown breakdown = pricingService.calculate(
                new PricingCommand(request.product(), request.taxNumber(), request.couponCode())
        );

        return CalculatePriceResponse.from(breakdown);
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@Valid @RequestBody PurchaseRequest request) {
        PurchaseResult result = purchaseService.purchase(
                new PurchaseCommand(
                        request.product(),
                        request.taxNumber(),
                        request.couponCode(),
                        request.paymentProcessor()
                )
        );

        return PurchaseResponse.from(result);
    }
}
