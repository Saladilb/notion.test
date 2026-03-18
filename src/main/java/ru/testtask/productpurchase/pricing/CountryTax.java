package ru.testtask.productpurchase.pricing;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public enum CountryTax {
    GERMANY("DE", "Germany", new BigDecimal("0.19"), Pattern.compile("^DE\\d{9}$")),
    ITALY("IT", "Italy", new BigDecimal("0.22"), Pattern.compile("^IT\\d{11}$")),
    FRANCE("FR", "France", new BigDecimal("0.20"), Pattern.compile("^FR[A-Z]{2}\\d{9}$")),
    GREECE("GR", "Greece", new BigDecimal("0.24"), Pattern.compile("^GR\\d{9}$"));

    private final String countryCode;
    private final String displayName;
    private final BigDecimal taxRate;
    private final Pattern taxNumberPattern;

    CountryTax(String countryCode, String displayName, BigDecimal taxRate, Pattern taxNumberPattern) {
        this.countryCode = countryCode;
        this.displayName = displayName;
        this.taxRate = taxRate;
        this.taxNumberPattern = taxNumberPattern;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public static Optional<CountryTax> fromTaxNumber(String taxNumber) {
        if (taxNumber == null || taxNumber.isBlank()) {
            return Optional.empty();
        }

        String normalizedTaxNumber = taxNumber.trim().toUpperCase();
        return Arrays.stream(values())
                .filter(value -> value.taxNumberPattern.matcher(normalizedTaxNumber).matches())
                .findFirst();
    }
}

