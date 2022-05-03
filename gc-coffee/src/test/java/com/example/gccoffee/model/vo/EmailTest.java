package com.example.gccoffee.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("유요하지 않은 이메일 포맷은 IllegalArgumentException 발생.")
    public void testInvalidEmail() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> { new Email("wrong"); }),
                () -> assertThrows(IllegalArgumentException.class, () -> { new Email("aa@ema"); }),
                () -> assertThrows(IllegalArgumentException.class, () -> { new Email("aa@email."); })
        );
    }

    @Test
    @DisplayName("올바른 이메일 형식일 경우 객체가 생성된다.")
    public void testValidEmail() {
        String correctAddress = "hello@gmail.com";
        Email email = new Email(correctAddress);

        assertTrue(email.getAddress().equals(correctAddress));
    }

    @Test
    @DisplayName("동등성 확인. 주소가 달라도 값이 같으면 두 객체는 같다.")
    public void testEqEmail() {
        String address = "hello@gmail.com";
        Email email = new Email(address);
        Email email2 = new Email(address);

        assertEquals(email, email2);
    }

}