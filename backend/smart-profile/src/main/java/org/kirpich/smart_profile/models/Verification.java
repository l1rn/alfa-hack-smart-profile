package org.kirpich.smart_profile.models;

import jakarta.persistence.*;

@Entity
@Table(name="verifications")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
