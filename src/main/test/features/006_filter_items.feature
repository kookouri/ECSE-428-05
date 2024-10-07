Feature: Filter items (ID006)
As a user, I want to be able to filter items in the system.

    Background:
        Given the following items exist in the system
        | id | name | price | description | category | 
        | 01 | science hoodie | 40 | soft hoodie for science students | Clothing | 
        | 02 | med hoodie | 40 | soft hoodie for med students | Clothing | 
        | 03 | nurse hoodie | 40 | soft hoodie for nursing students | Clothing | 
        | 04 | signals & networks book | 250 | textbook for ecse 316 | Textbook | 
        | 05 | operating systems book | 250 | textbook for ecse 427 | Textbook |

    Scenario: Successfully filter the items to only view the clothing. 
        When the user attempts to only view the clothing items in the system
        Then the following items shall be presented
            | id | name | price | description | category |
            | 01 | science hoodie | 40 | soft hoodie for science students | Clothing | 
            | 02 | med hoodie | 40 | soft hoodie for med students | Clothing | 
            | 03 | nurse hoodie | 40 | soft hoodie for nursing students | Clothing | 
        
    Scenario: Successfully filter the items to only view the textbooks. 
        When the user attempts to only view the clothing items in the system
        Then the following items shall be presented
            | id | name | price | description | category |
            | 04 | signals & networks book | 250 | textbook for ecse 316 | Textbook | 
            | 05 | operating systems book | 250 | textbook for ecse 427 | Textbook |
