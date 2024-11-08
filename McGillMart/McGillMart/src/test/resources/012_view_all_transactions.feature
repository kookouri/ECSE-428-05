Feature: View all transactions (ID012)
  As a user, I would like to generate a transaction summary to view all of my transaction history

  Background:
    Given the following users exist in the system
      | id  | name    | email            | phone        | password |
      | 01  | Alice   | alice@mail.com   | 555-123-4567 | pass123  |
      | 02  | Bob     | bob@mail.com     | 555-987-6543 | pass456  |
      | 03  | Charles | charles@mail.com | 555-087-6543 | pass789  |


    And the following transactions exist in the system
      | id | amount | dateOfPurchase | user  | description    |
      | 01 | 100.50 | 2023-08-01     | Alice | green hat      |
      | 02 | 250.00 | 2023-09-15     | Alice | textbooks      |
      | 03 | 50.00  | 2023-08-21     |  Bob  |  volleyball    |
      | 04 | 150.00 | 2023-09-10     |  Bob  | concert ticket |

  Scenario Outline: Successfully view transaction history for a user
    Given I am logged in with "<email>"
    When I request to view my transaction history
    Then I should see <transactionCount> transactions in my history
    And I should see the following transaction details:
      | amount    | dateOfPurchase | description |
      | <amount1> | <date1>        | <description1> |
      | <amount2> | <date2>        | <description2> |

    Examples:
      | email           | transactionCount | amount1 | date1       | amount2 | date2       | description1 | description2   |
      | alice@mail.com  | 2                | 100.50  | 2023-08-01  | 250.00  | 2023-09-15  | green hat    | textbooks      |
      | bob@mail.com    | 2                | 50.00   | 2023-08-21  | 150.00  | 2023-09-10  | volleyball   | concert ticket |

  Scenario: View transaction history with no transactions
    Given I am logged in with "charles@mail.com"
    When I request to view my transaction history
    Then I should see 0 transactions in my history