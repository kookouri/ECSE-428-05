Feature: Leave a review on an item (ID007)
  As a user, I want to leave a review on items so that I can let others know about my experience with the product.

  Background:
    Given the following users exist in the system (ID007)
    | id  | name  | email            | phone        | password |
    | 01  | Alice | alice@mail.com   | 555-123-4567 | pass123  |
    | 02  | Bob   | bob@mail.com     | 555-987-6543 | pass456  |

    And the following items exist in the system (ID007)
    | id  | name              | price | description                 | category  |
    | 01  | McGill Hoodie      | 40    | Soft hoodie with McGill logo| Clothing  |
    | 02  | Calculus Textbook  | 250   | Comprehensive math textbook | Textbook  |

  Scenario: Successfully leave a review for an item
    When the user with email "alice@mail.com" submits a review for "McGill Hoodie" with a rating of 5 and a comment "Loved the hoodie!" (ID007)
    Then a new review for "McGill Hoodie" with rating 5 and comment "Loved the hoodie!" shall be created (ID007)
    And the total number of reviews for "McGill Hoodie" shall be 1 (ID007)

  Scenario: Invalid input when leaving a review
    When the user with email "alice@mail.com" submits a review for "McGill Hoodie" with a rating of 6 and a comment "Great hoodie!" (ID007)
    Then an error message "Rating must be between 1 and 5" shall be displayed (ID007)

  Scenario: User fails to leave a review due to empty comment
    When the user with email "alice@mail.com" submits a review for "McGill Hoodie" with a rating of 4 and a comment " " (ID007)
    Then an error message "Comment cannot be empty" shall be displayed (ID007)
