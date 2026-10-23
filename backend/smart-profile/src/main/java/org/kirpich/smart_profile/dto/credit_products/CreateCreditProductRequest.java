package org.kirpich.smart_profile.dto.credit_products;

import lombok.Data;

@Data
public class CreateCreditProductRequest {
    private String title;
    private Long minAmount;
    private Long maxAmount;
    private Integer minTermMonths;
    private Integer maxTermMonths;
    private Double interestRate;
}
