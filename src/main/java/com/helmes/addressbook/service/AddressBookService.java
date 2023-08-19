package com.helmes.addressbook.service;

import com.helmes.addressbook.csv.CsvParser;
import com.helmes.addressbook.domain.Person;
import com.helmes.addressbook.exception.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddressBookService {

    private static final String BILL_NAME = "BILL";
    private static final String PAUL_NAME = "PAUL";

    private final List<String> errorList = new ArrayList<>();
    private int maleCounter;
    private Person oldestPerson;
    private Person bill;
    private Person paul;
    private long daysOlder;

    public void processAddressBook(final String path) throws IOException {
        try (var inputStream = AddressBookService.class.getClassLoader().getResourceAsStream(path);
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            var lineNumber = 0;
            String line;
            while (Objects.nonNull(line = reader.readLine())) {
                var person = CsvParser.parseLine(line, lineNumber++);
                countMalePersons(person);
                findOldestPerson(person);
                findBillAndPaul(person);
            }
            calculateBillPaulAgeDifference();
        } catch (CsvException e) {
            errorList.add(e.getMessage());
        }
        System.out.println(getResultMessage());
        errorList.forEach(System.out::println);
    }

    private void countMalePersons(final Person person) {
        if (person.isMale()) {
            maleCounter++;
        }
    }

    private void findOldestPerson(final Person person) {
        if (Objects.isNull(oldestPerson) || person.birthDate().isBefore(oldestPerson.birthDate())) {
            oldestPerson = person;
        }
    }

    private void findBillAndPaul(final Person person) {
        if (person.firstName().equalsIgnoreCase(BILL_NAME)) {
            bill = person;
        }
        if (person.firstName().equalsIgnoreCase(PAUL_NAME)) {
            paul = person;
        }
    }

    private void calculateBillPaulAgeDifference() {
        if (Objects.nonNull(bill) && Objects.nonNull(paul)) {
            daysOlder = daysOlder(bill, paul);
        }
    }

    private String getResultMessage() {
        return """
                Result:
                Number of males: %s
                Oldest person: %s
                Bill is older than Paul on %s days
                """.formatted(maleCounter, oldestPerson, daysOlder);
    }

    private long daysOlder(final Person bill, final Person paul) {
        return Duration.between(bill.birthDate(), paul.birthDate()).toDays();
    }
}
