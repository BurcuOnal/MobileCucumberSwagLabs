package com.Mobile.SwagLabs.stepDefinitions;

import com.Mobile.SwagLabs.StepImpl;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class LoginStepDef {
    private StepImpl stepImpl;

    public LoginStepDef() {
        stepImpl = new StepImpl();
    }

    @When("Tap standard_user button to autofill")
    public void tapStandard_userButtonToAutofill() {

        stepImpl.findElementByKeyWithoutAssert("standard_userBtn").click();
    }


    @And("User click the login button")
    public void userClickTheLoginButton() {
        stepImpl.findElementByKeyWithoutAssert("loginBtn").click();
        stepImpl.logger.info("User login successful");
    }

    @Then("User should navigate to Swag Labs home page")
    public void userShouldNavigateToSwagLabsHomePage() {
        stepImpl.findElementByKeyWithoutAssert("homePageMenuBtn");

    }


    @Given("User enter username as {string}")
    public void userEnterUsernameAs(String username) {
        stepImpl.sendKeysByKey("userNameTextBox",username);
    }

    @Given("User enter password as {string}")
    public void userEnterPasswordAs(String password) {
        stepImpl.sendKeysByKey("passwordTextBox",password);
    }

    @Given("verify Products page with title {string}")
    public void verifyProductsPageWithTitle(String title) {
        stepImpl.checkElementEqualsText("verifyProductPage", title);

    }


    @Then("verify login fail with an error {string}")
    public void verify_login_fail_with_an_error(String errorMessage) {
        stepImpl.checkElementEqualsText("loginErrorMessage", errorMessage);

    }


}
