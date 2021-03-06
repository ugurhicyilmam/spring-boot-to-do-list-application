package com.ugurhicyilmam.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue
    private long id;

    private String content;

    @ManyToOne
    private User user;

    private Date createdAt;

    public User getUser() {
        return null;
    }
}
