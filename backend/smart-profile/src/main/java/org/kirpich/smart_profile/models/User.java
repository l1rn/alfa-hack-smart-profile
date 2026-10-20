package org.kirpich.smart_profile.models;

import jakarta.persistence.*;
import lombok.*;
import org.kirpich.smart_profile.enums.UserRole;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "users")
@ToString(exclude = "password")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private boolean enabled = true;
}
