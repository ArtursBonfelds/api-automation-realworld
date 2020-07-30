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

  Scenario: Log in with created account - negative - wrong password

  Scenario: Log in with created account - negative - wrong email

  Scenario: Update user profile settings

  Scenario: Create a new post

  Scenario: Delete post

  Scenario: Modify post

  Scenario: Add comment to the post

  Scenario: Delete comment from post
