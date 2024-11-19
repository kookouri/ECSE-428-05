Feature: View cart (ID008)
  As a user, I would like to view the items that are in my cart.

  Background:
    Given the following users exist in the system (ID008)
      | id  | name  | email            | phone        | password     |
      | 01  | Alice | alice@mail.com   | 555-123-4567 | password123  |
      | 02  | Bob   | bob@mail.com     | 555-987-6543 | password456  |

    And the following items exist in Alice's cart (ID008)
      | id  | name               | price | description                 | category  | url         |
      | 01  | McGill Hoodie      | 40.0  | Soft hoodie with McGill logo| Clothing  | nothing.com |
      | 02  | Calculus Textbook  | 250.0 | Comprehensive math textbook | Textbook  | nothing.com |

    And the following items exist in Bob's cart (ID008)
      | id | name                    | price | description            | category | url         |
      | 03 | signals & networks book | 250.0 | textbook for ecse 316  | Textbook | nothing.com |
      | 04 | operating systems book  | 250.0 | textbook for ecse 427  | Textbook | nothing.com |

  Scenario Outline: Successfully view items in cart when logged in
    Given I am logged in as "alice@mail.com" (ID008)
    When I attempt to view my cart (ID008)
    Then I should see 2 items in my cart (ID008)
    And I should see the following items: (ID008)
      | name               | price | description                 | category | url         |
      | McGill Hoodie      | 40.0  | Soft hoodie with McGill logo| Clothing | nothing.com |
      | Calculus Textbook  | 250.0 | Comprehensive math textbook | Textbook | nothing.com |
    And the total cart value should be "290.00" (ID008)

  Scenario Outline: Successfully view items in cart with different user
    Given I am logged in as "bob@mail.com" (ID008)
    When I attempt to view my cart (ID008)
    Then I should see 2 items in my cart (ID008)
    And I should see the following items: (ID008)
      | name                    | price | description            | category | url         |
      | signals & networks book | 250.0 | textbook for ecse 316  | Textbook | nothing.com |
      | operating systems book  | 250.0 | textbook for ecse 427  | Textbook | nothing.com |
    And the total cart value should be "500.00" (ID008)

  Scenario Outline: View empty cart
    Given I am logged in as "alice@mail.com" (ID008)
    And I have removed all items from my cart (ID008)
    When I attempt to view my cart (ID008)
    Then I should see 0 items in my cart (ID008)
    And the total cart value should be "0.00" (ID008)