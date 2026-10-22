package org.kirpich.smart_profile.services;

import lombok.RequiredArgsConstructor;
import org.kirpich.smart_profile.dto.CreditProductRequest;
import org.kirpich.smart_profile.models.CreditProduct;
import org.kirpich.smart_profile.repositories.CreditProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditProductService {
    private final CreditProductRepository creditProductRepository;
    public void createCreditProduct(CreditProductRequest request){
        CreditProduct creditProduct = CreditProduct.builder()
                .title(request.getTitle())
                .minAmount(request.getMinAmount())
                .maxAmount(request.getMaxAmount())
                .minTermMonths(request.getMinTermMonths())
                .maxTermMonths(request.getMaxTermMonths())
                .interestRate(request.getInterestRate())
                .build();
        creditProductRepository.save(creditProduct);
    }
}
