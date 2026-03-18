package ru.testtask.productpurchase.catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private CouponType type;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    protected Coupon() {
    }

    public Coupon(Long id, String code, CouponType type, BigDecimal discountValue) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.discountValue = discountValue;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public CouponType getType() {
        return type;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }
}

