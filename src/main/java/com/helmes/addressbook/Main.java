package com.helmes.addressbook;

import com.helmes.addressbook.service.AddressBookService;

import java.io.IOException;

public class Main {

    private static final String FILE_PATH = "AddressBook";

    public static void main(String[] args) throws IOException {
        new AddressBookService().processAddressBook(FILE_PATH);
    }
}
