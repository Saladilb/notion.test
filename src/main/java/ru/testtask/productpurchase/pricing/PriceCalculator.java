package ru.testtask.productpurchase.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;
import ru.testtask.productpurchase.catalog.model.Coupon;
import ru.testtask.productpurchase.catalog.model.CouponType;
import ru.testtask.productpurchase.catalog.model.Product;

@Component
public class PriceCalculator {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    public PriceBreakdown calculate(Product product, Coupon coupon, CountryTax countryTax) {
        BigDecimal basePrice = money(product.getPrice());
        BigDecimal discountAmount = calculateDiscount(basePrice, coupon);
        BigDecimal subtotal = money(basePrice.subtract(discountAmount).max(BigDecimal.ZERO));
        BigDecimal taxAmount = money(subtotal.multiply(countryTax.getTaxRate()));
        BigDecimal finalPrice = money(subtotal.add(taxAmount));

        return new PriceBreakdown(
                product.getId(),
                product.getName(),
                basePrice,
                discountAmount,
                subtotal,
                countryTax.getTaxRate().multiply(ONE_HUNDRED),
                taxAmount,
                finalPrice,
                coupon != null ? coupon.getCode() : null,
                countryTax.getCountryCode()
        );
    }

    private BigDecimal calculateDiscount(BigDecimal basePrice, Coupon coupon) {
        if (coupon == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal discountAmount = switch (coupon.getType()) {
            case FIXED -> coupon.getDiscountValue();
            case PERCENT -> basePrice
                    .multiply(coupon.getDiscountValue())
                    .divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
        };

        return money(discountAmount.min(basePrice));
    }

    private BigDecimal money(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}

