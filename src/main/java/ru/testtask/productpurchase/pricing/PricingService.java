package ru.testtask.productpurchase.pricing;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testtask.productpurchase.catalog.model.Coupon;
import ru.testtask.productpurchase.catalog.model.Product;
import ru.testtask.productpurchase.catalog.repository.CouponRepository;
import ru.testtask.productpurchase.catalog.repository.ProductRepository;
import ru.testtask.productpurchase.common.error.BadRequestException;

@Service
@Transactional(readOnly = true)
public class PricingService {

    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private final PriceCalculator priceCalculator;

    public PricingService(
            ProductRepository productRepository,
            CouponRepository couponRepository,
            PriceCalculator priceCalculator
    ) {
        this.productRepository = productRepository;
        this.couponRepository = couponRepository;
        this.priceCalculator = priceCalculator;
    }

    public PriceBreakdown calculate(PricingCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new BadRequestException("Unknown product: " + command.productId()));

        Coupon coupon = resolveCoupon(command.couponCode());
        CountryTax countryTax = CountryTax.fromTaxNumber(command.taxNumber())
                .orElseThrow(() -> new BadRequestException("Invalid tax number"));

        return priceCalculator.calculate(product, coupon, countryTax);
    }

    private Coupon resolveCoupon(String couponCode) {
        if (couponCode == null || couponCode.isBlank()) {
            return null;
        }

        return couponRepository.findByCodeIgnoreCase(couponCode.trim())
                .orElseThrow(() -> new BadRequestException("Unknown coupon code: " + couponCode));
    }
}

