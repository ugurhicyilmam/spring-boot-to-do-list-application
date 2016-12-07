package com.ugurhicyilmam.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Todo {

    @Id
    @GeneratedValue
    private long id;

    private String content;

    @ManyToOne
    private User user;

    private Date createdAt;
}
