package com.Mobile.SwagLabs.stepDefinitions;

import com.Mobile.SwagLabs.StepImpl;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductsStepDef

{
    private LoginStepDef loginStepDef;
    private StepImpl stepImpl;

    public ProductsStepDef() {
        stepImpl = new StepImpl();
        loginStepDef = new LoginStepDef();
    }


    @Then("the product is listed with correct name {string} and price {string}")
    public void theProductIsListedWithCorrectNameAndPrice(String productName, String price)
    {
        stepImpl.checkElementEqualsText("sauceLabsBackpackFirstProductName",productName);
        stepImpl.checkElementEqualsText("sauceLabsBackpackFirstProductPrice",price);
    }



}
