package org.kirpich.smart_profile.services;

import lombok.RequiredArgsConstructor;
import org.kirpich.smart_profile.enums.BkiStatus;
import org.kirpich.smart_profile.enums.EgrulStatus;
import org.kirpich.smart_profile.enums.FinalVerdict;
import org.kirpich.smart_profile.models.Company;
import org.kirpich.smart_profile.models.Verification;
import org.kirpich.smart_profile.repositories.VerificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationRepository verificationRepository;

    private BkiStatus creditHistoryCheck(Company company){
        return BkiStatus.BKI_PASSED;
    }
    private EgrulStatus egrulCheck(Company company){
        return EgrulStatus.EGRUL_PASSED;
    }

    public Verification createVerification(Company company){
        BkiStatus bki = creditHistoryCheck(company);
        EgrulStatus egrul = bki == BkiStatus.BKI_PASSED ? egrulCheck(company) : EgrulStatus.EGRUL_FAILED;
        FinalVerdict verdict = (bki == BkiStatus.BKI_PASSED && egrul == EgrulStatus.EGRUL_PASSED)
                ? FinalVerdict.VERIFICAITON_APPROVED : FinalVerdict.VERIFICATION_REJECTED;

        return verificationRepository.save(Verification.builder()
                .bkiStatus(bki).egrulStatus(egrul).finalVerdict(verdict)
                .issueDate(LocalDateTime.now()).build());
    }
}
