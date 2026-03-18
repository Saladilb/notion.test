package ru.testtask.productpurchase.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductPurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCalculatePrice() throws Exception {
        mockMvc.perform(post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "product": 1,
                                  "taxNumber": "DE123456789",
                                  "couponCode": "D15"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value(1))
                .andExpect(jsonPath("$.productName").value("Iphone"))
                .andExpect(jsonPath("$.finalPrice").value(101.15))
                .andExpect(jsonPath("$.countryCode").value("DE"));
    }

    @Test
    void shouldPurchaseWithPaypal() throws Exception {
        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "product": 1,
                                  "taxNumber": "IT12345678900",
                                  "couponCode": "D15",
                                  "paymentProcessor": "paypal"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.paymentProcessor").value("paypal"))
                .andExpect(jsonPath("$.finalPrice").value(103.70));
    }

    @Test
    void shouldReturnValidationErrorsForInvalidTaxNumber() throws Exception {
        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "product": 1,
                                  "taxNumber": "IT123",
                                  "couponCode": "D15",
                                  "paymentProcessor": "paypal"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[0].field").value("taxNumber"));
    }

    @Test
    void shouldReturnBusinessErrorForUnknownCoupon() throws Exception {
        mockMvc.perform(post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "product": 1,
                                  "taxNumber": "DE123456789",
                                  "couponCode": "P50"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Unknown coupon code: P50"));
    }

    @Test
    void shouldReturnBusinessErrorForRejectedStripePayment() throws Exception {
        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "product": 2,
                                  "taxNumber": "DE123456789",
                                  "paymentProcessor": "stripe"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Payment failed in stripe"));
    }
}
