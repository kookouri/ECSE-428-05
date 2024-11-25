Feature: View all transactions (ID012)
  As a user, I would like to generate a transaction summary to view all of my transaction history

  Background:
    Given the following users exist in the system (ID012)
      | id  | name    | email            | phone        | password  |
      | 1  | Alice   | alice@mail.com   | 555-123-4567 | pass1234  |
      | 2  | Bob     | bob@mail.com     | 555-987-6543 | pass4564  |
      | 3  | Charles | charles@mail.com | 555-087-6543 | pass7894  |

    And the following transactions exist in the system (ID012)
      | userId  | amount | dateOfPurchase | description                                          |
      | 1       | 100.50 | 2023-08-01     | Items purchased: ENGL hoodie, Red Pencil Case        |
      | 2       | 250.00 | 2023-09-15     | Items purchased: ECSE hoodie, Desautels Pencil Case  |


  Scenario Outline: Successfully view transaction history for Alice
    Given I am logged in with "alice@mail.com" (ID012)
    When I request to view my transaction history (ID012)
    Then I should see 1 transactions in my history (ID012)
    And I should see the following transaction details: (ID012)
      | amount    | description    |
      | 100.50    | Items purchased: ENGL hoodie, Red Pencil Case |

  Scenario Outline: Successfully view transaction history for Bob
    Given I am logged in with "bob@mail.com" (ID012)
    When I request to view my transaction history (ID012)
    Then I should see 1 transactions in my history (ID012)
    And I should see the following transaction details: (ID012)
      | amount    | description    |
      | 250.00    | Items purchased: ECSE hoodie, Desautels Pencil Case |



  Scenario Outline: View transaction history with no transactions
    Given I am logged in with "charles@mail.com" (ID012)
    When I request to view my transaction history (ID012)
    Then I should see 0 transactions in my history (ID012)

