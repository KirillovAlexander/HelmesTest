package com.helmes.addressbook.exception;

public class CsvException extends Exception {

    public CsvException(final String message, final int lineNumber) {
        super("%s. Line number: %s. Please check the source file."
                      .formatted(message, lineNumber));
    }
}
