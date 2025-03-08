package com.grimoire.model.grimoire;

import com.grimoire.dto.user.UserResponseDto;
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

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "NOME", nullable = false)
    private String name;

    @Column(name = "ID_IMAGEM")
    private String pictureID;

    public UserResponseDto toDto() {
        return UserResponseDto.builder()
                .username(this.username)
                .email(this.email)
                .name(this.name)
                .pictureID(this.pictureID)
                .build();
    }
}
