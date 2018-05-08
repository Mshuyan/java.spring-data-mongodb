package com.shuyan.demo1.domain;

import org.springframework.data.annotation.Id;

public class Dog {
    @Id
    private String id;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Dog(String username) {
        this.username = username;
    }
}
