Feature: View items (ID005)
As a user, I want to be able to view all items in the system.
Background:
    Given the following items exist in the system
    | id | name                    | price | description                         | category | 
    | 01 | science hoodie          | 40    | soft hoodie for science students    | Clothing | 
    | 02 | med hoodie              | 40    | soft hoodie for med students        | Clothing | 
    | 03 | nurse hoodie            | 40    | soft hoodie for nursing students    | Clothing | 
    | 04 | signals & networks book | 250   | textbook for ecse 316               | Textbook | 
    | 05 | operating systems book  | 250   | textbook for ecse 427               | Textbook |

Scenario: Successfully view all items.
    When the user attempts to view all items in the system
    Then the following items shall be presented
        | id | name                    | price | description                      | category |
        | 01 | science hoodie          | 40    | soft hoodie for science students | Clothing |
        | 02 | med hoodie              | 40    | soft hoodie for med students     | Clothing |
        | 03 | nurse hoodie            | 40    | soft hoodie for nursing students | Clothing |
        | 04 | signals & networks book | 250   | textbook for ecse 316            | Textbook |
        | 05 | operating systems book  | 250   | textbook for ecse 427            | Textbook |