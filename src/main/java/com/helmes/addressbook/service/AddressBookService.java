package com.helmes.addressbook.service;

import com.helmes.addressbook.csv.CsvParser;
import com.helmes.addressbook.domain.Person;
import com.helmes.addressbook.domain.Sex;
import com.helmes.addressbook.exception.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class AddressBookService {

    public void processAddressBook(final String path) {
        var errorList = new ArrayList<String>();
        var maleCount = 0;
        Person oldestPerson = null;
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            errorList.add(e.getMessage());
        }
        System.out.println(getResultMessage(maleCount, oldestPerson));
        errorList.forEach(System.out::println);
    }

    private String getResultMessage(final int numberOfMales, final Person oldestPerson) {
        return String.format("Result:%nNumber of males: %s%nOldest person: %s", numberOfMales, oldestPerson);
    }
}
