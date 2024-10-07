Feature: Create a new user
As a user, I wish to register my account in the system

    Background:
        Given the following guests exist in the system
            | email          | password | name | phoneNumber   |
            | jeff@gmail.com | pass1    | Jeff | (555)555-5555 |
            | john@gmail.com | pass2    | John | (444)444-4444 |

    Scenario Outline: A user registers successfully
        When a new user attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>"
        Then a new user account shall exist with "<email>", "<password>", "<name>", and "<phoneNumber>"
        Then the number of users in the system shall be "3"

        Examples:
            | email          | password | name | phoneNumber   |
            | lisa@gmail.com | pass4    | Lisa | (888)888-8888 |
            | liam@yahoo.com | pass5    | Liam | (777)777-7777 |
            | owen@gmail.com | pass10   |      | (888)888-5555 |
            | noah@gmail.com | pass11   | Noah |               |

    Scenario Outline: A user registers unsuccessfully
        When a new user attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>"
        Then the following "<error>" shall be raised
        Then the number of guests in the system shall be "2"
        Then the following users shall exist in the system
            | email          | password | name | phoneNumber   |
            | jeff@gmail.com | pass1    | Jeff | (555)555-5555 |
            | john@gmail.com | pass2    | John | (444)444-4444 |

        Examples: 
            | email           | password | name | emergencyContact | error                                   |
            | manager@ap.com  | pass1    | Paul | (111)111-1111    | Email cannot be manager@ap.com          |
            | jeff@ap.com     | pass1    | Jeff | (111)111-1111    | Email domain cannot be @ap.com          |
            | jeff@gmail.com  | pass2    | Jeff | (111)777-7777    | Email already linked to a guest account |
            | bart @ ap.com   | pass3    | Bart | (444)666-6666    | Email must not contain any spaces       |
            | dony@gmail@.com | pass4    | Dony | (777)555-7777    | Invalid email                           |
            | kyle@gmail.     | pass5    | Kyle | (666)777-6666    | Invalid email                           |
            | greg.ap@com     | pass6    | Greg | (777)888-7777    | Invalid email                           |
            | @gmail.com      | pass7    | Otto | (111)777-6666    | Invalid email                           |
            | karl@.com       | pass8    | Karl | (111)777-6661    | Invalid email                           |
            |                 | pass9    | Vino | (777)888-5555    | Email cannot be empty                   |
            | luke@gmail.com  |          | Luke | (999)888-5555    | Password cannot be empty                |