package com.ugurhicyilmam.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    private long id;
    private String username;
    private String password;
}
