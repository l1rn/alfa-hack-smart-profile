package org.kirpich.smart_profile.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name="creditProducts")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Long minAmount;
    private Long maxAmount;
    private Integer minTermMonths;
    private Integer maxTermMonths;
    private Double interestRate;
}
