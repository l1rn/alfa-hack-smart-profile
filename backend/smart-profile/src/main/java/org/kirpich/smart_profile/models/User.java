package org.kirpich.smart_profile.models;

import jakarta.persistence.*;
import lombok.*;
import org.kirpich.smart_profile.enums.UserRole;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fio;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
}
