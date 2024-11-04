Feature: Add Item to Cart List (ID009)
  As a user, I want to add items to my cart so that I can checkout more items at a time.

  Background:
    Given the following user exists in the system (ID009)
      | id | name     | email                | phone        | password      |
      | 0  | Jeff 	  | jeff@mail.mcgill.ca  | 123-456-7890 | validPass@123 |
	
    And I am logged in with the email "jeff@mail.mcgill.ca" (ID009)
    And the following items exist in the system (ID009)
      | id | name                    | price   | description                      | category 	    |
      | 0  | ECSE hoodie             | 50.0    | Hoodie for ECSE students         | Clothing 	    | 
      | 1  | Deshautel Pencil Case   | 12.0    | Pencil case with Deshautel brand | School Supply   | 
      | 2  | Macroeconomics Textbook | 399.99  | Textbook for Macroeconomics      | Textbook 	    |

  Scenario Outline: Successfully add an item to the cart.
    Given I have an empty cart (ID009)
    When I attempt to add an item with name "<name>" to my cart (ID009)
    Then I should see the item with name "<name>" and description "<description>" in my cart (ID009)
    And the number of items in my cart should be "1" (ID009)
	
	Examples:
      | id | name                    | price   | description                      | category 	    |
      | 0  | ECSE hoodie             | 50.0    | Hoodie for ECSE students         | Clothing 	    | 
      | 1  | Deshautel Pencil Case   | 12.0    | Pencil case with Deshautel brand | School Supply   | 
      | 2  | Macroeconomics Textbook | 399.99  | Textbook for Macroeconomics      | Textbook 	    |
	

  Scenario: Unsuccessfully add a non-existent item to the cart.
    Given I have an empty cart (ID009)
    When I attempt to add an item with name "Microeconomics Textbook" to my cart (ID009)
    Then the error "Invalid item: Item does not exist" shall be raised (ID009)
    And the number of items in my cart should be "0" (ID009) 

  Scenario: Successfully increase the amount of an item in the cart.
    Given the following items are in my cart (ID009)
      | id | name                    | price   | description                 | category |
      | 0  | ECSE hoodie             | 50.0    | Hoodie for ECSE students    | Clothing |  
      | 2  | Macroeconomics Textbook | 399.99  | Textbook for Macroeconomics | Textbook |
	
    When I attempt to add an item with name "Deshautel Pencil Case" to my cart (ID009)
    Then I should see the item with name "Deshautel Pencil Case" and description "Pencil case with Deshautel brand" in my cart (ID009)
    And the number of items in my cart should be "3" (ID009)
	