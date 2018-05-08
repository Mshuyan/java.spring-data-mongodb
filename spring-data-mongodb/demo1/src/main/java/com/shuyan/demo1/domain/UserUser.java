package com.shuyan.demo1.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "user1")
public class UserUser {
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

    public UserUser(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
