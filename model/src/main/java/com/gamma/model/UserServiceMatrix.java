package com.gamma.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserServiceMatrix {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
}
