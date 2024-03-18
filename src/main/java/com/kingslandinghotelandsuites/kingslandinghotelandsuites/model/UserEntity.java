package com.kingslandinghotelandsuites.kingslandinghotelandsuites.model;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.enums.Gender;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class UserEntity extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private Gender gender;
    private Roles roles;
    private Boolean isVerified;



}
