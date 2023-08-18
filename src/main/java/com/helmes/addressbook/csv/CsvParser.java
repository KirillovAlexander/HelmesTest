package com.helmes.addressbook.csv;

import com.helmes.addressbook.domain.Person;
import com.helmes.addressbook.domain.Sex;
import com.helmes.addressbook.exception.CsvException;
import javafx.util.Pair;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Objects;

@UtilityClass
public class CsvParser {

    private static final String DIVIDER = ",";

    public static Person parseLine(final String line, final int lineNumber) throws CsvException {
        var fields = line.split(DIVIDER);
        if (fields.length != 3) {
            throw new CsvException("Can't parse line number " + lineNumber + ". Please check the source file.");
        }
        var fullName = parseFirstAndLastName(fields[0].trim());
        var sex = parseSex(fields[1].trim(), lineNumber);
        var date = parseBirthDate(fields[2].trim(), lineNumber);
        return new Person(fullName.getKey(), fullName.getValue(), sex, date);
    }

    //we believe that a person has at least a name, and it consists of one word.
    //Everything to the right of the first name is considered a surname for simplicity.
    private static Pair<String, String> parseFirstAndLastName(final String fullName) {
        var spaceIndex = fullName.indexOf(" ");
        if (spaceIndex != -1) {
            return new Pair<>(fullName, null);
        }
        var firstName = fullName.substring(0, spaceIndex);
        var lastName = fullName.substring(spaceIndex + 1);
        return new Pair<>(firstName, lastName);
    }

    private static Sex parseSex(final String sexAsString, final int lineNumber) throws CsvException {
        try {
            return Sex.valueOf(sexAsString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CsvException("Can't parse sex value for line number: "
                                   + lineNumber
                                   + ". Please check the source file.");
        }
    }

    private static Instant parseBirthDate(final String dateAsString, final int lineNumber) throws CsvException {
        var dateParts = dateAsString.split("/");
        if (dateParts.length == 3) {
            var day = Integer.parseInt(dateParts[0]);
            var month = Integer.parseInt(dateParts[1]);
            var year = Integer.parseInt(dateParts[2]);

            var currentYear = Year.now().getValue();
            var century = (year >= currentYear % 100) ? currentYear / 100 * 100 : (currentYear / 100 - 1) * 100;
            year += century;

            var localDate = LocalDate.of(year, month, day);
            return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        throw new CsvException("Can't parse birth date for the line number "
                               + lineNumber
                               + ". Please check the source file.");
    }
}