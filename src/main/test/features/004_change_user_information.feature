Feature: Change User Information (ID004)
  As a user, I want to update my profile information.

  Background:
    Given the following users exist in the system
    | id  | name         | email                | phone        | password      |
    | 01  | John Doe     | john@example.com     | 123-456-4890 | oldPassword1  |
    | 02  | Jane Smith   | jane@example.com     | 987-654-3210 | oldPassword2  |
    | 03  | Bob Johnson  | bob@example.com      | 555-123-4567 | oldPassword3  |

  Scenario: Successfully update user name and phone number
    When the user updates the "name" field to "Johnny Doe" and the "phone" field to "321-654-9870" for user with id "01"
    Then the user profile should show the updated "name" as "Johnny Doe"
    And the "phone" field should display "321-654-9870"
    And the "email" field should remain "john@example.com"

  Scenario: Successfully update user email
    When the user updates the "email" field to "johnny@example.com" for user with id "01"
    Then the user profile should show the updated "email" as "johnny@example.com"
    And the "name" field should remain "John Doe"
    And the "phone" field should remain "123-456-4890"

  Scenario: Successfully update user password
    When the user changes the "password" field for user with id "02" to "newPassword2"
    Then the user should be able to log in with the new password "newPassword2"
    And the old password "oldPassword2" should no longer be valid
