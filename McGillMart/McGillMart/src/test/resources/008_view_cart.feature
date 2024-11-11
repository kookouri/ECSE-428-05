Feature: View cart (ID008)
  As a user, I would like to view the items that are in my cart.

  Background:
    Given the following users exist in the system (ID008)
      | id  | name  | email            | phone        | password |
      | 01  | Alice | alice@mail.com   | 555-123-4567 | pass123  |
      | 02  | Bob   | bob@mail.com     | 555-987-6543 | pass456  |

    And the following items exist Alice's cart (ID008)
      | id  | name               | price | description                 | category  | url |
      | 01  | McGill Hoodie      | 40.0  | Soft hoodie with McGill logo| Clothing  | nothing.com |
      | 02  | Calculus Textbook  | 250.0   | Comprehensive math textbook | Textbook  |  nothing.com |

    And the following items exist in Bob's cart (ID008)
      | id | name                    | price | description            | category | url |
      | 03 | signals & networks book | 250.0 | textbook for ecse 316  | Textbook | nothing.com |
      | 04 | operating systems book  | 250.0 | textbook for ecse 427  | Textbook | nothing.com |

  Scenario Outline: Successfully view items in cart when logged in
    Given I am logged in as "Alice"
    When I attempt to view my cart
    Then I should see 2 items in my cart
    And I should see the following items:
      | name               | price | description                 | category | url |
      | McGill Hoodie      | 40.0    | Soft hoodie with McGill logo| Clothing | nothing.com |
      | Calculus Textbook  | 250.0   | Comprehensive math textbook | Textbook | nothing.com |
    And the total cart value should be "$290.00"

  Scenario Outline: Successfully view items in cart with different user
    Given I am logged in as "Bob"
    When I attempt to view my cart
    Then I should see 2 items in my cart
    And I should see the following items:
      | name                    | price | description            | category | url |
      | signals & networks book | 250.0 | textbook for ecse 316  | Textbook | nothing.com |
      | operating systems book  | 250.0 | textbook for ecse 427  | Textbook | nothing.com |
    And the total cart value should be "$500.00"

  Scenario Outline: View empty cart
    Given I am logged in as "Alice"
    And I have removed all items from my cart
    When I attempt to view my cart
    Then I should see 0 items in my cart
    And the total cart value should be "$0.00"