Feature: Create a new user (ID001)
As a user, I would like to register my account in the system.

  Background: 
    Given the following user accounts exist in the system (ID001)
      | id | name | email            | phone        | password     |
      | 01 | Jeff | jeff@gmail.com   | 555-555-5555 | password1    |
      | 02 | John | john@gmail.com   | 444-444-4444 | password2    |     

  Scenario Outline: A user registers successfully
    When a new user attempts to register with "<email>", "<password>", "<name>", and "<phone>" (ID001)
    Then a new user account shall exist with "<email>", "<password>", "<name>", and "<phone>" (ID001)
    Then the number of users in the system shall be "3" (ID001)

    Examples:
      | email          | password     | name | phone        |
      | lisa@gmail.com | password4    | Lisa | 888-888-8888 |
      | liam@yahoo.com | password5    | Liam | 777-777-7777 |
      | owen@gmail.com | pass1word0   | Owen | 888-888-5555 |
      | noah@gmail.com | pass1word1   | Noah | 123-456-7890 |

  Scenario Outline: A user registers unsuccessfully
    When a new user attempts to register with "<email>", "<password>", "<name>", and "<phone>" (ID001)
    Then the following "<error>" shall be raised (ID001)
    Then the number of users in the system shall be "2" (ID001)
    Then the following users shall exist in the system (ID001)
      | id | name | email          | phone        | password     |
      | 01 | Jeff | jeff@gmail.com | 555-555-5555 | password1    |
      | 02 | John | john@gmail.com | 444-444-4444 | password2    |

    Examples: 
      | email            | phone        | password  | error                                     |
      | jeff@gmail.com   | 111-777-7777 | password1 | The email is already associated to a user |
      | bart @ gmail.com | 444-666-6666 | password2 | Email must not contain any spaces         |
      | dony@gmail@.com  | 777-555-7777 | password3 | Invalid email                             |
      | kyle@gmail.      | 666-777-6666 | password4 | Invalid email                             |
      | greg.gmail@com   | 777-888-7777 | password5 | Invalid email                             |
      | @gmail.com       | 111-777-6666 | password6 | Invalid email                             |
      | karl@.com        | 111-777-6661 | password7 | Invalid email                             |
      |                  | 777-888-5555 | password8 | Email cannot be empty                     |
      | luke@gmail.com   | 999-888-5555 |           | Password cannot be empty                  |
