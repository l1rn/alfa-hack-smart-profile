package org.kirpich.smart_profile.services;

import lombok.RequiredArgsConstructor;
import org.kirpich.smart_profile.dto.PaginatedResponse;
import org.kirpich.smart_profile.dto.application.ApplicationResponse;
import org.kirpich.smart_profile.models.Application;
import org.kirpich.smart_profile.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    @Autowired
    private final ApplicationRepository applicationRepository;
    public void createApplication(Application application){

    }

    lpublic PaginatedResponse<ApplicationResponse> getApplicationList(Pageable p){
        Page<Application> applications = applicationRepository.findAllPageable(p);

        List<ApplicationResponse> content = applications.stream()
                .filter(a -> a.getCompany() != null)
                .filter(a -> a.getVerification() != null)
                .map(ApplicationResponse::from)
                .toList();

        return new PaginatedResponse<>(
            content,
            applications.getNumber(),
            applications.getSize(),
            applications.getTotalElements(),
            applications.getTotalPages()
        );
    }

}
