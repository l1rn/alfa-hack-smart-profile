package org.kirpich.smart_profile.models;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String inn;
    private String ogrn;
    private String legalAddress;
    private Long revenue;
    private Integer ownerId;
}
