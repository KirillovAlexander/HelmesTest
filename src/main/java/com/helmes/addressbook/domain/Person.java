package com.helmes.addressbook.domain;

import java.time.Instant;

public record Person(String firstName, String lastName, Sex sex, Instant birthDate) {

    public boolean isMale() {
        return sex.equals(Sex.MALE);
    }
}
