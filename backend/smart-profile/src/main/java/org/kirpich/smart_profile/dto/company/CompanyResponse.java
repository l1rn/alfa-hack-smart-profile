package org.kirpich.smart_profile.dto.company;

import org.kirpich.smart_profile.models.Application;
import org.kirpich.smart_profile.models.Company;

public record CompanyResponse(
        String name,
        String inn,
        String ogrn,
        String legalAddress,
        Long revenue
) {
    public static CompanyResponse from(Company c){
        return new CompanyResponse(
                c.getName(),
                c.getInn(),
                c.getOgrn(),
                c.getLegalAddress(),
                c.getRevenue()
        );
    }
}
