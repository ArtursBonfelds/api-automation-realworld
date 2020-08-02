Feature: Realworld-example Feature
  Background:
    Given the user creates an account with following data:
      | user --> username | RANDOM_USERNAME           |
      | user --> email    | RANDOM_EMAIL              |
      | user --> password | totalyAwsomeAndUniquePass |
    Then  user gets status code "200"

  @run
  Scenario: Validate the account
    Then the path "user" contains the following values:
      | username | RANDOM_USERNAME |
      | email    | RANDOM_EMAIL    |

  Scenario: Log in with created account
    # Find endpoint for _API_USERS_LOGIN_ !!! and define it in RealWorldSteps.java
    When the user logs into account with following data:
      | user --> email    | RANDOM_EMAIL              |
      | user --> password | totalyAwsomeAndUniquePass |
    Then user gets status code "000"

  Scenario: Log in with created account - negative - wrong password
    When the user logs into account with following data:
      | user --> email    | RANDOM_EMAIL                 |
      | user --> password | totalyAwsomeAndIncorrectPass |
    Then user gets status code "000"

  Scenario: Log in with created account - negative - wrong email
    When the user logs into account with following data:
      | user --> email    | unregistered@email.com    |
      | user --> password | totalyAwsomeAndUniquePass |
    Then user gets status code "000"

  Scenario: Update user profile settings
    # Implement the undefined step
    # Find the missing XXX & YYY JSON keys
    When the user updates the settings using the following data:
      | XXX --> YYY | https://i.kym-cdn.com/photos/images/original/001/250/147/e6f.jpg |
      | XXX --> YYY | new@email.com                                                    |
      | XXX --> YYY | Some random facts about biography                                |
      | XXX --> YYY | newUsername                                                      |
      | XXX --> YYY | newUniquePassword                                                |
    Then user gets status code "000"
    And the path "XXX" contains the following values:
      | YYY | https://i.kym-cdn.com/photos/images/original/001/250/147/e6f.jpg |
      | YYY | new@email.com                                                    |
      | YYY | Some random facts about biography                                |
      | YYY | newUsername                                                      |
      | YYY | newUniquePassword                                                |

  Scenario: Create a new post
    # Implement the undefined step
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | XXX --> YYY | Example of Article title when creating |
      | XXX --> YYY | Example of Article description         |
      | XXX --> YYY | Example of Article body                |
    Then user gets status code "000"
    # Find the missing XXX & YYY JSON keys
    And  the path "XXX" contains the following values:
      | YYY | Example of Article title when creating |
      | YYY | Example of Article description         |
      | YYY | Example of Article body                |
    And  the path "XXX --> author" contains the following values:
      | email    | RANDOM_EMAIL    |
      | username | RANDOM_USERNAME |

  Scenario: Delete post
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | XXX --> YYY | Example of Article title when deleting |
      | XXX --> YYY | Example of Article description         |
      | XXX --> YYY | Example of Article body                |
    Then user gets status code "000"
    # Find the path of slug
    And  the user received one value in path "article --> slug" and sets session variable with this name "slug"
    # Implement the undefined step
    # Get the value of slug, by using sessionVariableCalled method, and use it to delete the article.
    When the user deletes article
    Then user gets status code "000"

  Scenario: Modify post
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | XXX --> YYY | Example of Article title when modifying |
      | XXX --> YYY | Example of Article description          |
      | XXX --> YYY | Example of Article body                 |
    Then user gets status code "000"
    # Find the path of slug
    And  the user received one value in path "article --> slug" and sets session variable with this name "slug"
    # Implement the undefined step
    # Find the missing XXX & YYY JSON keys
    # Get the value of slug, by using sessionVariableCalled method, and use it to modify the article.
    When the user modifies the post using the following data:
      | XXX --> YYY | Example of Article title when modified - Random |
      | XXX --> YYY | Example of Article description modified         |
      | XXX --> YYY | Example of Article body modified                |
    Then user gets status code "000"
    # Find the missing XXX & YYY JSON keys
    And the path "XXX" contains the following values:
      | YYY | Example of Article title when modified - Random |
      | YYY | Example of Article description modified         |
      | YYY | Example of Article body modified                |
    And  the path "XXX --> author" contains the following values:
      | email    | RANDOM_EMAIL    |
      | username | RANDOM_USERNAME |

  Scenario: Add comment to the post
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | XXX --> YYY | Example of Article title when adding comment |
      | XXX --> YYY | Example of Article description               |
      | XXX --> YYY | Example of Article body                      |
    Then user gets status code "000"
    # Implement the undefined step
    # Find the missing XXX & YYY JSON keys
    When the user adds a comment to the post with the following data:
      | XXX --> YYY | This is my comment |
    Then user gets status code "000"
    # Find the missing XXX & YYY JSON keys
    And  the value of path "XXX --> YYY" is "This is my comment"

  Scenario: Delete comment from post
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | XXX --> YYY | Article title example when deleting comment |
      | XXX --> YYY | Example of Article description              |
      | XXX --> YYY | Example of Article body                     |
    Then user gets status code "000"
    # Find the path of slug
    And  the user received one value in path "article --> slug" and sets session variable with this name "slug"
    # Get the value of slug, by using sessionVariableCalled method, and use it to add comment.
    # Find the missing XXX & YYY JSON keys
    When the user adds a comment to the post with the following data:
      | XXX --> YYY | This is my comment |
    Then user gets status code "000"
    # Find the path of id
    And the user received one value in path "comment --> id" and sets session variable with this name "comment_id"
    # Get the value of slug and comment_id, by using sessionVariableCalled method, and use it to delete the user comment.
    When the user deletes the comment
    Then user gets status code "000"
