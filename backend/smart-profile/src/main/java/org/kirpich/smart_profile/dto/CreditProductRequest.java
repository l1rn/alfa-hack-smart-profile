package org.kirpich.smart_profile.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreditProductRequest {
    private String title;
    private Long minAmount;
    private Long maxAmount;
    private Integer minTermMonths;
    private Integer maxTermMonths;
    private Double interestRate;
}
