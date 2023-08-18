package com.helmes.addressbook;

import com.helmes.addressbook.service.AddressBookService;

public class Main {

    private static final String FILE_PATH = "AddressBook";

    public static void main(String[] args) {
        new AddressBookService().processAddressBook(FILE_PATH);
    }
}
