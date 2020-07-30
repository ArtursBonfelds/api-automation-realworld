package com.testing.gherkinsDefinitions;

import com.testing.serenitySteps.RealworldSteps;
import cucumber.api.DataTable;
import cucumber.api.java.en.When;

import java.io.IOException;

public class RealWorldDefinitions {
  @When("^the user creates an account with following data:$")
  public void theUserCreatesAnAccountWithFollowingData(DataTable dataTable) throws IOException {
    RealworldSteps.createAnAccount(dataTable);
  }
}
