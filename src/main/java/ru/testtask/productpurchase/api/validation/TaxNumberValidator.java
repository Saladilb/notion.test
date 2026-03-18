package ru.testtask.productpurchase.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.testtask.productpurchase.pricing.CountryTax;

public class TaxNumberValidator implements ConstraintValidator<ValidTaxNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        return CountryTax.fromTaxNumber(value).isPresent();
    }
}

