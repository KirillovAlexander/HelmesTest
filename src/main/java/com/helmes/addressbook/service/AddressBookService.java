package com.helmes.addressbook.service;

import com.helmes.addressbook.csv.CsvParser;
import com.helmes.addressbook.domain.Sex;
import com.helmes.addressbook.exception.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AddressBookService {

    public void processAddressBook(final String path) {
        var errorList = new ArrayList<String>();
        var maleCount = 0;
        try (var inputStream = AddressBookService.class.getClassLoader().getResourceAsStream(path);
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            int lineNumber = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                var person = CsvParser.parseLine(line, lineNumber++);
                if (person.sex().equals(Sex.MALE)) {
                    maleCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            errorList.add(e.getMessage());
        }
        System.out.println(getResultMessage(maleCount));
        errorList.forEach(System.out::println);
    }

    private String getResultMessage(final int numberOfMales) {
        return String.format("Result:%nNumber of males: %s", numberOfMales);
    }
}
