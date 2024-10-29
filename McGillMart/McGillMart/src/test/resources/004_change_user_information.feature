Feature: Change User Information (ID004)
  As a user, I want to update my profile information.

  Background:
    Given the following users exist in the system (ID004)
    | id  | name         | email                | phone        | password      |
    | 01  | John Doe     | john@example.com     | 123-456-4890 | oldPassword1  |
    | 02  | Jane Smith   | jane@example.com     | 987-654-3210 | oldPassword2  |
    | 03  | Bob Johnson  | bob@example.com      | 555-123-4567 | oldPassword3  |

  Scenario: Successfully update user name and phone number
    When the user updates the name field to "Johnny Doe" and the phone field to "321-654-9870" for user with email "john@example.com" (ID004)
    Then the name field should display "Johnny Doe" (ID004)
    And the phone field should display "321-654-9870" (ID004)
    And the email field should display "john@example.com" (ID004)

  Scenario: Successfully update user email
    When the user updates the email field to "johnny@example.com" for user with email "john@example.com" (ID004)
    Then the email field should display "johnny@example.com" (ID004)
    And the name field should display "John Doe" (ID004)
    And the phone field should display "123-456-4890" (ID004)

  Scenario: Successfully update user password
    When the user changes the password field for user with email "bob@example.com" to "newPassword2" (ID004)
    Then the user should be able to log in with the new password "newPassword2" (ID004)
    And the old password "oldPassword2" should no longer be valid (ID004)
