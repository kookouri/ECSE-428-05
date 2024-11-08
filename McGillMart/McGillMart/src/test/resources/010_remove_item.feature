Feature: Remove item (ID010)
As a user, I want to be able to remove items from the shopping cart.
Background:
    Given the following items exist in the system (ID010)
    | id | name                    | price   | description                         | category | 
    | 01 | science hoodie          | 40.0    | soft hoodie for science students    | Clothing | 
    | 02 | med hoodie              | 40.0    | soft hoodie for med students        | Clothing | 
    | 03 | nurse hoodie            | 40.0    | soft hoodie for nursing students    | Clothing | 
    | 04 | signals & networks book | 250.0   | textbook for ecse 316               | Textbook | 
    | 05 | operating systems book  | 250.0   | textbook for ecse 427               | Textbook |

Scenario: Successfully remove an item from the system.
    When the user attempts to remove the item with id "02" (ID010)
    Then the item with id "02" shall no longer be present in the system (ID010)
    And the remaining items shall be (ID010)
        | id | name                    | price   | description                      | category |
        | 01 | science hoodie          | 40.0    | soft hoodie for science students | Clothing |
        | 03 | nurse hoodie            | 40.0    | soft hoodie for nursing students | Clothing |
        | 04 | signals & networks book | 250.0   | textbook for ecse 316            | Textbook |
        | 05 | operating systems book  | 250.0   | textbook for ecse 427            | Textbook |

Scenario: Attempt to remove an item that does not exist.
    When the user attempts to remove the item with id "10" (ID010)
    Then an error message "Item not found" shall be displayed (ID010)
    And the following items shall remain in the system (ID010)
        | id | name                    | price   | description                      | category |
        | 01 | science hoodie          | 40.0    | soft hoodie for science students | Clothing |
        | 02 | med hoodie              | 40.0    | soft hoodie for med students     | Clothing |
        | 03 | nurse hoodie            | 40.0    | soft hoodie for nursing students | Clothing |
        | 04 | signals & networks book | 250.0   | textbook for ecse 316            | Textbook |
        | 05 | operating systems book  | 250.0   | textbook for ecse 427            | Textbook |
