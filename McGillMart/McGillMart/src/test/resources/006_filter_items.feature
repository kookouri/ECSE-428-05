Feature: Filter items (ID006)
As a user, I want to be able to view all items in the system.

Background:
    Given the following items exist in the system (ID006)
    | id | name                    | price | description                         | category | 
    | 01 | science hoodie          | 40    | soft hoodie for science students    | Clothing | 
    | 02 | med hoodie              | 40    | soft hoodie for med students        | Clothing | 
    | 03 | nurse hoodie            | 40    | soft hoodie for nursing students    | Clothing | 
    | 04 | signals & networks book | 250   | textbook for ecse 316               | Textbook | 
    | 05 | operating systems book  | 250   | textbook for ecse 427               | Textbook |

Scenario: Successfully search items by name.
    When the user searches for items with the name containing "hoodie" (ID006)
    Then the following items shall be presented (ID006)
        | id | name           | price | description                      | category |
        | 01 | science hoodie | 40    | soft hoodie for science students | Clothing |
        | 02 | med hoodie     | 40    | soft hoodie for med students     | Clothing |
        | 03 | nurse hoodie   | 40    | soft hoodie for nursing students | Clothing |

Scenario: Successfully filter the items to only view the clothing.
    When the user attempts to only view the "Clothing" items in the system (ID006)
    Then the following items shall be presented (ID006)
        | id | name           | price | description                      | category |
        | 01 | science hoodie | 40    | soft hoodie for science students | Clothing |
        | 02 | med hoodie     | 40    | soft hoodie for med students     | Clothing |
        | 03 | nurse hoodie   | 40    | soft hoodie for nursing students | Clothing |

Scenario: Successfully filter the items to only view the textbooks.
    When the user attempts to only view the "Textbook" items in the system (ID006)
    Then the following items shall be presented (ID006)
        | id | name                    | price | description           | category |
        | 04 | signals & networks book | 250   | textbook for ecse 316 | Textbook |
        | 05 | operating systems book  | 250   | textbook for ecse 427 | Textbook |
