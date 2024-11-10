Feature: View items (ID005)
As a user, I want to be able to view all items in the system.
Background:
    Given the following items exist in the system (ID005)
    | id | name                    | price   | description                         | category | url | 
    | 01 | science hoodie          | 40.0    | soft hoodie for science students    | Clothing | nothing.com |
    | 02 | med hoodie              | 40.0    | soft hoodie for med students        | Clothing | nothing.com |
    | 03 | nurse hoodie            | 40.0    | soft hoodie for nursing students    | Clothing | nothing.com |
    | 04 | signals & networks book | 250.0   | textbook for ecse 316               | Textbook | nothing.com |
    | 05 | operating systems book  | 250.0   | textbook for ecse 427               | Textbook | nothing.com |

Scenario: Successfully view all items.
    When the user attempts to view all items in the system (ID005)
    Then the following items shall be presented (ID005)
        | id | name                    | price   | description                      | category | url |
        | 01 | science hoodie          | 40.0    | soft hoodie for science students | Clothing | nothing.com |
        | 02 | med hoodie              | 40.0    | soft hoodie for med students     | Clothing | nothing.com |
        | 03 | nurse hoodie            | 40.0    | soft hoodie for nursing students | Clothing | nothing.com |
        | 04 | signals & networks book | 250.0   | textbook for ecse 316            | Textbook | nothing.com |
        | 05 | operating systems book  | 250.0   | textbook for ecse 427            | Textbook | nothing.com |