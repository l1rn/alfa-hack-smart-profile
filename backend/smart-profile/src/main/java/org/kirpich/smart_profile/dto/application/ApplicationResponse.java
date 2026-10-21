package org.kirpich.smart_profile.dto.application;

import org.kirpich.smart_profile.enums.ApplicationStatus;
import org.kirpich.smart_profile.enums.BkiStatus;
import org.kirpich.smart_profile.enums.EgrulStatus;
import org.kirpich.smart_profile.enums.FinalVerdict;
import org.kirpich.smart_profile.models.Application;

public record ApplicationResponse(
        Long id,
        String companyName,
        String companyLegalAddress,
        EgrulStatus egrulStatus,
        BkiStatus bkiStatus,
        FinalVerdict finalVerdict
) {
    public static ApplicationResponse from(Application application){
        return new ApplicationResponse(
                application.getId(),
                application.getCompany().getName(),
                application.getCompany().getLegalAddress(),
                application.getVerification().getEgrulStatus(),
                application.getVerification().getBkiStatus(),
                application.getVerification().getFinalVerdict()
        );
    }
}
