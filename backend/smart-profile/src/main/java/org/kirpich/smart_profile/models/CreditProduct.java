package org.kirpich.smart_profile.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name="creditProducts")
@Entity
@Getter
@Setter
public class CreditProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Long minAmount;
    private Long maxAmount;
    private Date minTermMonths;
    private Date maxTermMonths;
    private Double interestRate;
}
