Feature: User Login (ID002)
  As a registered user of McGillMart
  I want to log in to my account
  So that I can access the shopping dashboard

  Background:
    Given the following users exist in the system (ID002)
    | id | name         | email                | phone        | password    |
    | 01 | John Doe     | user123@example.com  | 123-456-4890 | password123 |
    | 02 | Jane Smith   | jane@example.com     | 987-654-3210 | oldPassword2|
    | 03 | Bob Johnson  | bob@example.com      | 555-123-4567 | oldPassword3|

  Scenario: Successful login with valid credentials
    When the user attempts to login with the email "user123@example.com" and the password "password123" (ID002)
    Then "John Doe" should be displayed in the account section (ID002)

  Scenario: Failed login due to incorrect password
    When the user attempts to login with the email "user123@example.com" and the password "pass123" (ID002)
    Then an error message should be displayed saying "Incorrect password, please try again" (ID002)

  Scenario: Failed login due to unregistered email
    When the user attempts to login with the email "unknown@example.com" and the password "pass123" (ID002)
    Then an error message should be displayed saying "Email not found, please register" (ID002)

  Scenario: Failed login due to missing credentials
    When the user attempts to login with the email "" and the password "" (ID002)
    Then an error message should be displayed saying "Please enter both email and password" (ID002)
