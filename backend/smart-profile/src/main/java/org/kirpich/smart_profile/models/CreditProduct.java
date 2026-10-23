package org.kirpich.smart_profile.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name="credit_products")
@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CreditProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(nullable = false)
    private Long minAmount;

    @Column(nullable = false)
    private Long maxAmount;

    @Column(nullable = false)
    private Integer minTermMonths;

    @Column(nullable = false)
    private Integer maxTermMonths;

    @Column(nullable = false)
    private Double interestRate;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
