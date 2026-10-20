package org.kirpich.smart_profile.models;

import jakarta.persistence.*;

import java.util.Date;

@Table(name="applications")
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
