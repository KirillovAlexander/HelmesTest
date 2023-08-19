# Address Book Application

This application is designed to read an AddressBook file and answer specific questions about the data within it.

## Questions
- How many males are in the address book?
- Who is the oldest person in the address book?
- How many days older is Bill than Paul?

## Assumptions and comments

Please note the following assumptions and considerations made while developing the application:

- To run the program, you need to execute the `main` method in the `Main` class.
- Answers to the questions are displayed in the console.
- In order to avoid unnecessary complexity and maintain a single pass through the file, answers are obtained in one iteration.
The time complexity is **O(n)**.
- The program is designed to handle errors gracefully.
Of course, we could have chosen the fail-fast approach as well, but in this case I decided to take the opposite approach.
If incorrect or improperly formatted data is encountered in any file line, the data is added to an error list and displayed to the user
in the console at the end of the program execution.
- For the sake of parsing simplicity, it is assumed that the first word in a line is the first name,
while the rest of the line is considered the last name. However, in real-life scenarios, variations can occur.
- To simplify parsing of birth dates (as only the last two digits of the birth year are provided),
the following logic is applied:
If the last two digits of the birth year are **greater** than the last two digits of the current year,
it is assumed the person was born in the previous century. If **less**, then it is assumed they were born in the current century.
In reality, this logic might not hold true, especially for centenarians.
- For answering the third question (the age difference between Bill and Paul), the last occurrences of individuals with those names are considered.
In a real-world address book, additional data would be required to accurately identify individuals. 
Relying solely on names would be insufficient.

