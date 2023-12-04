package com.example.midterm.model;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    public Role() {
       super();
    }

    public Role(String authority) {
        this.authority = authority;
    }

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public Long getRoleId() {
        return roleId;
    }
}
