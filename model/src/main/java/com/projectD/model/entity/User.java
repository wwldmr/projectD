//package com.projectD.model.entity;
//
//import com.projectD.model.enums.Role;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Builder
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "users_seq", allocationSize = 1)
//    private Long id;
//
//    @Column(name = "username")
//    private String username;
//
//    @Column(name = "password")
//    private String password;
//
//    @Column(name = "email", nullable = false, unique = true)
//    private String email;
//
//    @Column(name = "roles")
//    private Role role;
//
//    @Setter
//    private String setUsername;
//}
