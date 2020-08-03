@run
Feature: Realworld-example Feature
  Background:
    Given the user creates an account with following data:
      | user --> username | RANDOM_USERNAME           |
      | user --> email    | RANDOM_EMAIL              |
      | user --> password | totalyAwsomeAndUniquePass |
    Then  user gets status code "200"


  Scenario: Validate the account
    Then the path "user" contains the following values:
      | username | RANDOM_USERNAME |
      | email    | RANDOM_EMAIL    |

  Scenario: Log in with created account
    # Find endpoint for _API_USERS_LOGIN_ !!! and define it in RealWorldSteps.java
    When the user logs into account with following data:
      | user --> email    | RANDOM_EMAIL              |
      | user --> password | totalyAwsomeAndUniquePass |
    Then user gets status code "200"

  Scenario: Log in with created account - negative - wrong password
    When the user logs into account with following data:
      | user --> email    | RANDOM_EMAIL                 |
      | user --> password | totalyAwsomeAndIncorrectPass |
    Then user gets status code "401"


  Scenario: Log in with created account - negative - wrong email
    When the user logs into account with following data:
      | user --> email    | unregistered@email.com    |
      | user --> password | totalyAwsomeAndUniquePass |
    Then user gets status code "401"


  Scenario: Update user profile settings
    # Implement the undefined step
    # Find the missing XXX & YYY JSON keys
    When the user updates the settings using the following data:
      | user --> image    | https://i.kym-cdn.com/photos/images/original/001/250/147/e6f.jpg |
      | user --> email    | new@email.com                                                    |
      | user --> bio      | Some random facts about biography                                |
      | user --> username | newUsername                                                      |
      | user --> password | newUniquePassword                                                |
    Then user gets status code "200"
    And the path "user" contains the following values:
      | image    | https://i.kym-cdn.com/photos/images/original/001/250/147/e6f.jpg |
      | email    | new@email.com                                                    |
      | bio      | Some random facts about biography                                |
      | username | newUsername                                                      |
      | password | newUniquePassword                                                |


  Scenario: Create a new post
    # Implement the undefined step
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | article --> title       | Example of Article title when creating |
      | article --> description | Example of Article description         |
      | article --> body        | Example of Article body                |
    Then user gets status code "200"
    # Find the missing XXX & YYY JSON keys
    And  the path "article" contains the following values:
      | title       | Example of Article title when creating |
      | description | Example of Article description         |
      | body        | Example of Article body                |
    And  the path "article --> author" contains the following values:
      | email    | RANDOM_EMAIL    |
      | username | RANDOM_USERNAME |


  Scenario: Delete post
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | article --> title       | Example of Article title when deleting |
      | article --> description | Example of Article description         |
      | article --> body        | Example of Article body                |
    Then user gets status code "200"
    # Find the path of slug
    And  the user received one value in path "article --> slug" and sets session variable with this name "slug"
    # Implement the undefined step
    # Get the value of slug, by using sessionVariableCalled method, and use it to delete the article.
    When the user deletes article
    Then user gets status code "200"


  Scenario: Modify post
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | article --> title       | Example of Article title when modifying |
      | article --> description | Example of Article description          |
      | article --> body        | Example of Article body                 |
    Then user gets status code "200"
    # Find the path of slug
    And  the user received one value in path "article --> slug" and sets session variable with this name "slug"
    # Implement the undefined step
    # Find the missing XXX & YYY JSON keys
    # Get the value of slug, by using sessionVariableCalled method, and use it to modify the article.
    When the user modifies the post using the following data:
      | article --> title       | Example of Article title when modified - Random |
      | article --> description | Example of Article description modified         |
      | article --> body        | Example of Article body modified                |
    Then user gets status code "200"
    # Find the missing XXX & YYY JSON keys
    And the path "article" contains the following values:
      | title       | Example of Article title when modified - Random |
      | description | Example of Article description modified         |
      | body        | Example of Article body modified                |
    And  the path "article --> author" contains the following values:
      | email    | RANDOM_EMAIL    |
      | username | RANDOM_USERNAME |


  Scenario: Add comment to the post
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | article --> title       | Example of Article title when adding comment |
      | article --> description | Example of Article description               |
      | article --> body        | Example of Article body                      |
    Then user gets status code "200"
    # Implement the undefined step
    # Find the missing XXX & YYY JSON keys
    When the user adds a comment to the post with the following data:
      | comment --> body | This is my comment |
    Then user gets status code "200"
    # Find the missing XXX & YYY JSON keys
    And  the value of path "comment --> body" is "This is my comment"


  Scenario: Delete comment from post
    # Find the missing XXX & YYY JSON keys
    When the user creates a post using the following data:
      | article --> title       | Article title example when deleting comment |
      | article --> description | Example of Article description              |
      | article --> body        | Example of Article body                     |
    Then user gets status code "200"
    # Find the path of slug
    And  the user received one value in path "article --> slug" and sets session variable with this name "slug"
    # Get the value of slug, by using sessionVariableCalled method, and use it to add comment.
    # Find the missing XXX & YYY JSON keys
    When the user adds a comment to the post with the following data:
      | comment --> body | This is my comment |
    Then user gets status code "200"
    # Find the path of id
    And the user received one value in path "comment --> id" and sets session variable with this name "comment_id"
    # Get the value of slug and comment_id, by using sessionVariableCalled method, and use it to delete the user comment.
    When the user deletes the comment
    Then user gets status code "200"
