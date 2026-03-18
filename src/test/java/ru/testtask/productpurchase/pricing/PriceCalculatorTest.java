package ru.testtask.productpurchase.pricing;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import ru.testtask.productpurchase.catalog.model.Coupon;
import ru.testtask.productpurchase.catalog.model.CouponType;
import ru.testtask.productpurchase.catalog.model.Product;

class PriceCalculatorTest {

    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    void shouldCalculatePriceWithPercentCouponAndTax() {
        Product product = new Product(1L, "Iphone", new BigDecimal("100.00"));
        Coupon coupon = new Coupon(1L, "P6", CouponType.PERCENT, new BigDecimal("6.00"));

        PriceBreakdown result = priceCalculator.calculate(product, coupon, CountryTax.GREECE);

        assertThat(result.discountAmount()).isEqualByComparingTo("6.00");
        assertThat(result.taxAmount()).isEqualByComparingTo("22.56");
        assertThat(result.finalPrice()).isEqualByComparingTo("116.56");
    }

    @Test
    void shouldCalculatePriceWithFixedCouponAndTax() {
        Product product = new Product(1L, "Iphone", new BigDecimal("100.00"));
        Coupon coupon = new Coupon(1L, "D15", CouponType.FIXED, new BigDecimal("15.00"));

        PriceBreakdown result = priceCalculator.calculate(product, coupon, CountryTax.GERMANY);

        assertThat(result.discountAmount()).isEqualByComparingTo("15.00");
        assertThat(result.taxAmount()).isEqualByComparingTo("16.15");
        assertThat(result.finalPrice()).isEqualByComparingTo("101.15");
    }

    @Test
    void shouldNotProduceNegativePriceForOversizedDiscount() {
        Product product = new Product(3L, "Case", new BigDecimal("10.00"));
        Coupon coupon = new Coupon(2L, "D50", CouponType.FIXED, new BigDecimal("50.00"));

        PriceBreakdown result = priceCalculator.calculate(product, coupon, CountryTax.ITALY);

        assertThat(result.discountAmount()).isEqualByComparingTo("10.00");
        assertThat(result.taxAmount()).isEqualByComparingTo("0.00");
        assertThat(result.finalPrice()).isEqualByComparingTo("0.00");
    }
}
