package com.grimoire.model.grimoire;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "USUARIOS")
@EqualsAndHashCode
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGIN", unique = true, nullable = false)
    private String username;

    @Column(name = "SENHA", nullable = false)
    private String password;

    @Column(name = "GMAIL", unique = true, nullable = false)
    private String gmail;

    @Column(name = "NOME", nullable = false)
    private String name;

    @Column(name = "URL_FOTO")
    private String pictureUrl;
}
