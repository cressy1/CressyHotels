package com.kingslandinghotelandsuites.kingslandinghotelandsuites.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "user-roles",
            joinColumns = @JoinColumn(name = "user-id",
            referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",
            referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();
}
