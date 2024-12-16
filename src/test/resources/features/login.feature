@login
Feature: Swag Labs App Login

 @PositiveLoginAutofill
 Scenario:Check the login functionality with autofill
  * Tap standard_user button to autofill
  * User click the login button
  * User should navigate to Swag Labs home page

 @PositiveLogin
 Scenario Outline: Login with valid user name and password
  * User enter username as "<username>"
  * User enter password as "<password>"
  * User click the login button
  * verify Products page with title "<title>"

  Examples:
   | username      | password     | title    |
   | standard_user | secret_sauce | PRODUCTS |
   | problem_user | secret_sauce | PRODUCTS |

 Scenario Outline: Login with invalid user name
  When User enter username as "<username>"
  And User enter password as "<password>"
  * User click the login button
  Then verify login fail with an error "<errorMessage>"

  Examples:
   | username        | password     | errorMessage                                                         |
   | invalidUsername | secret_sauce | Username and password do not match any user in this service.|
