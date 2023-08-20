import com.helmes.addressbook.service.AddressBookService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressBookServiceTest {

    @Test
    void givenAddressBookProcessed_whenResultMessageGenerated_thenOutputMatchesExpected() throws IOException {
        var outputStream = new ByteArrayOutputStream();
        var originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        var addressBookService = new AddressBookService();
        addressBookService.processAddressBook("AddressBookTestValid");

        System.setOut(originalOut);

        var capturedOutput = outputStream.toString().trim();

        var expectedOutput = """
                Result:
                Number of males: %s
                Oldest person: Person[firstName=Wes, lastName=Jackson, sex=MALE, birthDate=1974-08-13T23:00:00Z]
                Bill is older than Paul on %s days"""
                .formatted(3, 2862);

        assertEquals(expectedOutput, capturedOutput);
    }

    @Test
    void givenAddressBookWithWrongFormatProcessed_whenResultMessageGenerated_thenOutputMatchesExpected() throws IOException {
        var outputStream = new ByteArrayOutputStream();
        var originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        var addressBookService = new AddressBookService();
        addressBookService.processAddressBook("AddressBookTestWrongFormat");

        System.setOut(originalOut);

        var capturedOutput = outputStream.toString().trim();

        var expectedOutput = "Can't parse birth date. Line number: 0. Please check the source file.\r\n" +
                "Can't parse sex value. Line number: 1. Please check the source file.\r\n" +
                "Can't parse line. Line number: 2. Please check the source file.";

        assertTrue(capturedOutput.contains(expectedOutput));
    }
}
