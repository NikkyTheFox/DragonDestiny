
package com.example.user.entities;


import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {


    /**
     * User's login - unique identifier.
     */
    @Id
    private String login;

    /**
     * User's password.
     */
    @ToString.Exclude
    private String password;

    /**
     * User's name.
     */
    private String name;

}
