package org.example.webflux.model;

public class User {
    private int id;

    public User() {} // default constructor required for deserialization

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{id=" + id + '}';
    }
}

