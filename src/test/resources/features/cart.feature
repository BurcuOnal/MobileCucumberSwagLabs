@Cart
Feature: Carts Scenarios

  Background:
    * Tap standard_user button to autofill
    * User click the login button
    * User should navigate to Swag Labs home page

  @addProductToCart
  Scenario: Add a product to the cart
    When User click on the first product name
    Then Add product to the cart
    Then Go to cart
    And Check the product name in the cart


  @removeProductFromTheCart
  Scenario: Remove a product from the cart
    When User click on the first product name
    Then Add product to the cart
    Then Go to cart
    And Check the product name in the cart
    And Click remove button
    Then Check if cart is empty

  @addDifferentTypesProductAndInteractiveBadge
  Scenario: Add different types of products from products page and check interactiveBadge
    When First product add to cart
    When Second product add to cart
    And Check the interactive badge number is two
    Then Go to cart

