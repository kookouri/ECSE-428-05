Feature: View User Profile (ID003)
  As an end user, I would like to view my user profile so that I can see what information was stored.

  Background: 
    Given the following user accounts exist in the system (ID003)
      | id | name | email                | phone         | password      |
      | 0  | Jeff | jeff@mail.mcgill.ca  | 123-456-7890  | validPass@123 |
      | 1  | John | john@mail.mcgill.ca  | 098-765-4321  | validPass#234 |

  Scenario Outline: Successfully view one's user profile

    The user's email, name, and phone should be displayed.

    Given the user with email "<email>" is logged in (ID003)
    When the user attempts to view their profile (ID003)
    Then the name "<name>", email "<email>", and phone "<phone>" shall be displayed (ID003)

    Examples:
      | id | name | email                | phone         | password      |
      | 0  | Jeff | jeff@mail.mcgill.ca  | 123-456-7890  | validPass@123 |
      | 1  | John | john@mail.mcgill.ca  | 098-765-4321  | validPass#234 |

  Scenario: Unsuccessfully attempt to view one's profile when logged out

    The user should not be able to view their profile if they're not logged in.

    Given the user is not logged in (ID003)
    When the user attempts to view their profile (ID003)
    Then the error "User not logged in" shall be raised (ID003)
