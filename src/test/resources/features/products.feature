@ProductPage
Feature: Product Scenarios
  Background:
    * Tap standard_user button to autofill
    * User click the login button
    * User should navigate to Swag Labs home page


  Scenario Outline: Validate product info on Products page

    Then the product is listed with correct name "<productName>" and price "<price>"

    Examples:
      | productName             | price  |
      | Sauce Labs Backpack     | $29.99 |
     # | Sauce Labs Bike Light   | $9.99  |

