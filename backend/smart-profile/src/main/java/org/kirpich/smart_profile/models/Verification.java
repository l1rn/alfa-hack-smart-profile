package org.kirpich.smart_profile.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.kirpich.smart_profile.enums.BkiStatus;
import org.kirpich.smart_profile.enums.EgrulStatus;
import org.kirpich.smart_profile.enums.FinalVerdict;

import java.time.LocalDateTime;

@Entity
@Table(name="verifications")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private EgrulStatus egrulStatus;

    @Enumerated(EnumType.STRING)
    private BkiStatus bkiStatus;

    @Enumerated(EnumType.STRING)
    private FinalVerdict finalVerdict;

    private LocalDateTime issueDate;
    private Integer creditProductId;
}
