package com.rithik.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Address is required")
    private String address;

    // CARD, UPI or COD
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
}
