package com.example.gccoffee.dao;

public enum JdbcMessage {
    NO_INSERT("Noting was inserted"),
    NO_UPDATE("Nothing was updated");

    private final String message;

    JdbcMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
