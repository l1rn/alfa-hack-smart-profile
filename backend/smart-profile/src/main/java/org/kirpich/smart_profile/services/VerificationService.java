package org.kirpich.smart_profile.services;

import lombok.RequiredArgsConstructor;
import org.kirpich.smart_profile.enums.BkiStatus;
import org.kirpich.smart_profile.enums.EgrulStatus;
import org.kirpich.smart_profile.models.CreditProduct;
import org.kirpich.smart_profile.repositories.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {
    @Autowired
    private final VerificationRepository verificationRepository;

    private BkiStatus creditHistoryCheck(){
        return BkiStatus.BKI_PASSED;
    }
    private EgrulStatus egrulCheck(CreditProduct creditProduct){
        return EgrulStatus.EGRUL_PASSED;
    }

    public void createVerification(CreditProduct cp){
        BkiStatus creditHistory = creditHistoryCheck();
        EgrulStatus egrulStatus = EgrulStatus.EGRUL_FAILED;
        if(creditHistoryCheck() == BkiStatus.BKI_PASSED) {
            egrulStatus = egrulCheck(cp);
        }


    }
}
