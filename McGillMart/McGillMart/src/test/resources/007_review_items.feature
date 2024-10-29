Feature: Leave a review on an item (ID007)
As a user, I want to leave a review on items so that I can let others know about my experience with the product.

Background:
  Given the following users exist in the system (ID007)
    | id | name  | email            | phone        | password |
    | 01 | Alice | alice@mail.com   | 555-123-4567 | pass123  |
    | 02 | Bob   | bob@mail.com     | 555-987-6543 | pass456  |

  And the following items exist in the system (ID007)
    | id | name                    | price | description                  | category |
    | 01 | McGill Hoodie           | 40    | Soft hoodie with McGill logo  | Clothing |
    | 02 | Calculus Textbook       | 250   | Comprehensive math textbook   | Textbook |

Scenario Outline: User successfully leaves a review
  When the user with email "<email>", phone "<phone>", and password "<password>" submits a review for "<item_name>" with a rating of "<rating>" and a comment "<comment>" (ID007)
  Then a new review for "<item_name>" with rating "<rating>", comment "<comment>" and date "<datePosted>" shall be created (ID007)
  And the total number of reviews for "<item_name>" shall be "<review_count>" (ID007)

  Examples:
    | email            | phone        | password | item_name         | rating | comment                | datePosted | review_count |
    | alice@mail.com   | 555-123-4567 | pass123  | McGill Hoodie     | 5      | Loved the hoodie!      | 2024-01-01 | 1            |
    | bob@mail.com     | 555-987-6543 | pass456  | Calculus Textbook | 4      | Very helpful textbook  | 2024-01-01 | 1            |
    | alice@mail.com   | 555-123-4567 | pass123  | Calculus Textbook | 3      | A bit too complex      | 2024-01-01 | 2            |

Scenario Outline: User fails to leave a review due to invalid input
  When the user with email "<email>", phone "<phone>", and password "<password>" attempts to submit a review for "<item_name>" with a rating of "<rating>", comment "<comment>" and date "<datePosted>" (ID007)
  Then an error message "<error_message>" shall be displayed (ID007)
  And the total number of reviews for "<item_name>" shall be "0" (ID007)

  Examples:
    | email            | phone        | password  | item_name         | rating | comment       | error_message                    | datePosted| review_count |
    | alice@mail.com   | 555-123-4567 | pass123   | McGill Hoodie     | 6      | Great hoodie  | Rating must be between 1 and 5   | 2024-01-01 | 0            |
    | bob@mail.com     | 555-987-6543 | pass456   | Calculus Textbook | 0      | Informative   | Rating must be between 1 and 5   | 2024-01-01 | 0            |
    | alice@mail.com   | 555-123-4567 | pass123   | McGill Hoodie     | 4      |               | Comment cannot be empty          | 2024-01-01  | 0           |
    | bob@mail.com     | 555-987-6543 | wrongpass | Calculus Textbook | 5      | Excellent     | Invalid email or password        | 2024-01-01 | 0            |

Scenario: Unauthenticated user tries to leave a review
  When a non-authenticated user tries to submit a review for "McGill Hoodie" with rating "5" and comment "Great hoodie!" (ID007)
  Then an error message "You must be logged in to leave a review" shall be displayed (ID007)
  And the total number of reviews for "McGill Hoodie" shall be "0" (ID007)
