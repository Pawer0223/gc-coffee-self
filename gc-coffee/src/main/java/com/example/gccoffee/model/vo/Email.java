package com.example.gccoffee.model.vo;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final String ADDRESS_NULL = "address should not be null";
    private static final String ADDRESS_LENGTH_ERROR = "address length must be between 4 and 50 characters.";
    private static final String INVALID_ADDRESS = "Invalid email address";
    private static final String regex = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
    private static final int MIN_SIZE = 4;
    private static final int MAX_SIZE = 50;

    private final String address;

    public Email(String address) {
        Assert.notNull(address, ADDRESS_NULL);
        Assert.isTrue(address.length() >= MIN_SIZE && address.length() <= MAX_SIZE, ADDRESS_LENGTH_ERROR);
        Assert.isTrue(checkAddress(address), INVALID_ADDRESS);
        this.address = address;
    }

    private static boolean checkAddress(String address) {
        return Pattern.matches(regex, address);
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
