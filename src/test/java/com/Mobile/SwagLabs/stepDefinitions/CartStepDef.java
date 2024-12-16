package com.Mobile.SwagLabs.stepDefinitions;

import com.Mobile.SwagLabs.StepImpl;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CartStepDef {

        private StepImpl methods;

        public CartStepDef() {
            methods = new StepImpl();
        }

    @When("User click on the first product name")
    public void userClickOnTheFirstProductName() {
            methods.clickByKey("sauceLabsBackpackFirstProductName");
    }

    @Then("Add product to the cart")
    public void addProductToTheCart() throws InterruptedException {
            methods.clickByKey("sauceLabsBackpackFirstProductAddToCartBtn");
            methods.getElementWithKeyIfExists("cartAfterAddOneProduct","Fail! Number one on top of the cart couldn't find");

    }

    @Then("Go to cart")
    public void goToCart() throws InterruptedException {
            methods.clickByKey("cartButton");
            methods.getElementWithKeyIfExists("cartPageTitle","Fail! You're not in your cart page ");
    }

    @And("Check the product name in the cart")
    public void checkTheProductNameInTheCart() throws InterruptedException {
        methods.getElementWithKeyIfExists("sauceLabsBackpackFirstProductName","Fail! Product name couldn't find in the cart");

    }

    @And("Click remove button")
    public void clickRemoveButton() {
            methods.clickByKey("cartRemoveBtn");

    }

    @Then("Check if cart is empty")
    public void checkIfCartIsEmpty() throws InterruptedException {
            methods.waitBySecond(8);
            methods.getElementWithKeyIfNotExists("sauceLabsBackpackFirstProductName");

    }

    @When("First product add to cart")
    public void firstProductAddToCart() throws InterruptedException {
            methods.clickByKey("sauceLabsBackpackFirstProductAddToCartBtn");
            methods.getElementWithKeyIfExists("sauceLabsBackpackFirstProductRemoveBtn","Fail ! Remove button couldn't be found!");
    }

    @When("Second product add to cart")
    public void secondProductAddToCart() {
        methods.clickByKey("sauceLabsBackpackFirstProductAddToCartBtn");
    }

    @And("Check the interactive badge number is two")
    public void checkTheInteractiveBadgeNumberIsTwo() throws InterruptedException {
            methods.getElementWithKeyIfExists("interactiveBadgeNumberTwo","Fail! Badge number is not two!");
    }
}
