package ru.testtask.productpurchase.catalog.bootstrap;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.testtask.productpurchase.catalog.model.Coupon;
import ru.testtask.productpurchase.catalog.model.CouponType;
import ru.testtask.productpurchase.catalog.model.Product;
import ru.testtask.productpurchase.catalog.repository.CouponRepository;
import ru.testtask.productpurchase.catalog.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class CatalogDataInitializer implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedProducts();
        seedCoupons();
    }

    private void seedProducts() {
        saveProductIfMissing(1L, "Iphone", "100.00");
        saveProductIfMissing(2L, "Headphones", "20.00");
        saveProductIfMissing(3L, "Case", "10.00");
    }

    private void seedCoupons() {
        saveCouponIfMissing(1L, "D15", CouponType.FIXED, "15.00");
        saveCouponIfMissing(2L, "P10", CouponType.PERCENT, "10.00");
        saveCouponIfMissing(3L, "P100", CouponType.PERCENT, "100.00");
    }

    private void saveProductIfMissing(Long id, String name, String price) {
        if (!productRepository.existsById(id)) {
            productRepository.save(new Product(id, name, new BigDecimal(price)));
        }
    }

    private void saveCouponIfMissing(Long id, String code, CouponType type, String discountValue) {
        if (couponRepository.findByCodeIgnoreCase(code).isEmpty()) {
            couponRepository.save(new Coupon(id, code, type, new BigDecimal(discountValue)));
        }
    }
}
