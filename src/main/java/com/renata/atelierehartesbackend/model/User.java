package com.renata.atelierehartesbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.renata.atelierehartesbackend.enums.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String secondName;
    
    @Column(name = "email")
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "username")
    private String username;    
    
    @Column(name = "password")
    private String password;

    public User(String firstName, String secondName, String email, String username,Role role, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
}
