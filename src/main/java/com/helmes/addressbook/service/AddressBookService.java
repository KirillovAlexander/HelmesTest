package com.helmes.addressbook.service;

import com.helmes.addressbook.csv.CsvParser;
import com.helmes.addressbook.domain.Person;
import com.helmes.addressbook.domain.Sex;
import com.helmes.addressbook.exception.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

public class AddressBookService {

    private static final String BILL_NAME = "BILL";
    private static final String PAUL_NAME = "PAUL";

    public void processAddressBook(final String path) {
        var errorList = new ArrayList<String>();
        var maleCount = 0;
        Person oldestPerson = null;
        Person bill = null;
        Person paul = null;
        var daysOlder = 0L;
        try (var inputStream = AddressBookService.class.getClassLoader().getResourceAsStream(path);
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            int lineNumber = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                var person = CsvParser.parseLine(line, lineNumber++);
                if (person.sex().equals(Sex.MALE)) {
                    maleCount++;
                }
                if (Objects.isNull(oldestPerson) || person.birthDate().isBefore(oldestPerson.birthDate())) {
                    oldestPerson = person;
                }
                if (person.firstName().equalsIgnoreCase(BILL_NAME)) {
                    bill = person;
                }
                if (person.firstName().equalsIgnoreCase(PAUL_NAME)) {
                    paul = person;
                }
            }
            if (Objects.nonNull(bill) && Objects.nonNull(paul)) {
                daysOlder = daysOlder(bill, paul);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            errorList.add(e.getMessage());
        }
        System.out.println(getResultMessage(maleCount, oldestPerson, daysOlder));
        errorList.forEach(System.out::println);
    }

    private String getResultMessage(final int numberOfMales, final Person oldestPerson, final long daysOlder) {
        return String.format("Result:%nNumber of males: %s%nOldest person: %s%nBill is older than Paul on %s days",
                             numberOfMales,
                             oldestPerson,
                             daysOlder);
    }

    private long daysOlder(final Person bill, final Person paul) {
        return Duration.between(bill.birthDate(), paul.birthDate()).toDays();
    }
}
