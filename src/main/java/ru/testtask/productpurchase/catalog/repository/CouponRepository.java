package ru.testtask.productpurchase.catalog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.testtask.productpurchase.catalog.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCodeIgnoreCase(String code);
}

