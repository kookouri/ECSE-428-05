Feature: User Login (ID002)
  As a registered user of McGillMart
  I want to log in to my account
  So that I can access the shopping dashboard

  Background:
    Given the following users exist in the system:
      | email                 | password    | name       |
      | user123@example.com    | pass123     | John Doe   |
      | admin001@example.com   | adminPass   | Admin User |
      | inactive_user@example.com | inactivePass | Jane Smith |

  Scenario: Successful login with valid credentials
    Given the user is on the McGillMart login page
    And the user has an active McGillMart account with email "user123@example.com"
    When the user enters the email "user123@example.com" and the password "pass123"
    And the user clicks the Login button
    Then the user should be redirected to the shopping dashboard
    And "John Doe" should be displayed in the account section

  Scenario: Failed login due to incorrect password
    Given the user is on the McGillMart login page
    And the user has an active McGillMart account with email "user123@example.com"
    When the user enters the email "user123@example.com" but an incorrect password "wrongPass"
    And the user clicks the Login button
    Then an error message should be displayed saying "Incorrect password, please try again"
    And the user should remain on the login page

  Scenario: Failed login due to unregistered email
    Given the user is on the McGillMart login page
    And the user does not have a McGillMart account
    When the user enters an unregistered email "unknown@example.com" and any password
    And the user clicks the Login button
    Then an error message should be displayed saying "Email not found, please register"
    And the user should remain on the login page

  Scenario: Failed login due to missing credentials
    Given the user is on the McGillMart login page
    When the user clicks the "Login" button without entering an email or password
    Then an error message should be displayed saying "Please enter both email and password"
    And the user should remain on the login page
