package com.helmes.addressbook.csv;

import com.helmes.addressbook.domain.Sex;
import com.helmes.addressbook.exception.CsvException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvParserTest {

    @Test
    void testParseLine_validInput_returnsPersonObject() throws CsvException {
        var line = "John Doe, Male, 16/03/77";
        var lineNumber = 1;

        var result = CsvParser.parseLine(line, lineNumber);

        assertNotNull(result);
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        assertEquals(Sex.MALE, result.sex());
        var expectedBirthDate = LocalDate.of(1977, 3, 16).atStartOfDay(ZoneId.systemDefault()).toInstant();
        assertEquals(expectedBirthDate, result.birthDate());
    }

    @Test
    void testParseLine_invalidInput_throwsCsvException() {
        var line = "John Doe, Male"; // Missing birth date
        var lineNumber = 1;

        assertThrows(CsvException.class, () -> CsvParser.parseLine(line, lineNumber));
    }
}
