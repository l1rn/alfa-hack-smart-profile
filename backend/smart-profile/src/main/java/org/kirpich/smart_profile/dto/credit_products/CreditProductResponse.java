package org.kirpich.smart_profile.dto.credit_products;


import org.kirpich.smart_profile.models.CreditProduct;

import java.time.LocalDateTime;

public record CreditProductResponse(
        Integer id,
        String title,
        Long minAmount,
        Long maxAmount,
        Integer minTermMonths,
        Integer maxTermMonths,
        Double interestRate,
        Boolean active,
        LocalDateTime createdAt
) {
    public static CreditProductResponse from(CreditProduct cp){
        return new CreditProductResponse(
                cp.getId(),
                cp.getTitle(),
                cp.getMinAmount(),
                cp.getMaxAmount(),
                cp.getMinTermMonths(),
                cp.getMaxTermMonths(),
                cp.getInterestRate(),
                cp.getActive(),
                cp.getCreatedAt()
        );
    }
}
