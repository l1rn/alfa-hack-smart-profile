package org.kirpich.smart_profile.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.kirpich.smart_profile.enums.ApplicationStatus;


@Table(name="applications")
@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "verificationId")
    private Verification verification;

    private ApplicationStatus status;

}
