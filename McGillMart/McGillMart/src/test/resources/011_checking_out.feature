Feature: Checking Out The Shopping Cart (ID011)
    As a user, I want to check out my cart so I can purchase the items 

    Background:
        Given the following user exists in the system (ID011)
            | id | name     | email                | phone        | password      |
            | 0  | Jeff     | jeff@mail.mcgill.ca  | 123-456-7890 | validPass@123 |
	
        And I am logged in with the email "jeff@mail.mcgill.ca" (ID011)
        And the following items exist in the system (ID011)
            | id | name                    | price   | description                      | category 	    | url |
            | 0  | ECSE hoodie             | 50.0    | Hoodie for ECSE students         | Clothing 	    |  nothing.com |
            | 1  | Desautels Pencil Case   | 12.0    | Pencil case with Desautels brand | Stationary    |  nothing.com |
            | 2  | Macroeconomics Textbook | 399.99  | Textbook for Macroeconomics      | Textbook 	    | nothing.com |

    Scenario Outline: Successfully check out a non-empty shopping cart.
        Given the following items are in the user's shopping card (ID011)
            | id | name                    | price   | description                      | category 	    | url |
            | 0  | ECSE hoodie             | 50.0    | Hoodie for ECSE students         | Clothing 	    |  nothing.com |
            | 1  | Desautels Pencil Case   | 12.0    | Pencil case with Desautels brand | Stationary    |  nothing.com |
        When I attempt to check-out the shopping cart (ID011)
        Then the message "Successfully checked-out the shopping cart" will be raised. (ID011)
        And the number of items in my cart should be "0" (ID011)

    Scenario Outline: Unsuccessfully check out an empty shopping cart.
        Given the number of items in my cart is "0" (ID011)
        When I attempt to check-out the shopping cart (ID011)
        Then the error "Empty shopping cart: Item amount must be larger than 0." shall be raised (ID011)
        And the number of items in my cart should be "0" (ID011)
