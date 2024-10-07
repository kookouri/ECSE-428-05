Feature: View User Profile (ID003)
As an end user, I would like to view my user profile so that I can see what information was stored.

Background: 
    Given the following user accounts exist in the system
        | email                 | name      | password      | phoneNumber   |
        | jeff@mail.mcgill.ca   | Jeff      | validPass@123 | 123-456-7890  |
        | john@mail.mcgill.ca   | John      | validPass#234 | 098-765-4321  |

Scenario Outline: Successfully view one's user profile

    The user's email, name, and phoneNumber should be displayed.

    Given the user with email "<email>" is logged in
    When the user attempts to view their profile
    Then the name "<name>", email "<email>", and phoneNumber "<phoneNumber>" shall be displayed

    Examples:
        | email                 | name      | password      | phoneNumber   |
        | jeff@mail.mcgill.ca   | Jeff      | validPass@123 | 123-456-7890  |
        | john@mail.mcgill.ca   | John      | validPass#234 | 098-765-4321  |

Scenario: Unsuccessfully attempt to view one's profile when logged out

    The user should not be able to view their profile if they're not logged in.

    Given the user is not logged in
    When the user attempts to view their profile
    Then the error "User not logged in" shall be raised
