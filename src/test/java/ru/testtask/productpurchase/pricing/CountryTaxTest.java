package ru.testtask.productpurchase.pricing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CountryTaxTest {

    @Test
    void shouldResolveGermanyTaxNumber() {
        assertThat(CountryTax.fromTaxNumber("DE123456789"))
                .contains(CountryTax.GERMANY);
    }

    @Test
    void shouldResolveItalyTaxNumber() {
        assertThat(CountryTax.fromTaxNumber("IT12345678900"))
                .contains(CountryTax.ITALY);
    }

    @Test
    void shouldResolveFranceTaxNumber() {
        assertThat(CountryTax.fromTaxNumber("FRAB123456789"))
                .contains(CountryTax.FRANCE);
    }

    @Test
    void shouldRejectUnknownTaxNumberFormat() {
        assertThat(CountryTax.fromTaxNumber("DE123"))
                .isEmpty();
    }
}

