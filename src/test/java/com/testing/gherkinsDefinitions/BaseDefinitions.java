package com.testing.gherkinsDefinitions;

import com.testing.serenitySteps.BaseSteps;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class BaseDefinitions {

  @Then("^user gets status code \"([^\"]*)\"$")
  public void userGetsStatusCode(Integer statusCode) {
    BaseSteps.assertStatusCode(statusCode);
  }

  @And("^the value of path \"([^\"]*)\" is \"([^\"]*)\"$")
  public void theValueOfPathIs(String path, String expectedValue) {
    BaseSteps.valueOfPathIs(path, expectedValue);
  }

  @And("^the path \"([^\"]*)\" contains the following values:$")
  public void thePathContainsTheFollowingValues(String path, DataTable dataTable) {
    BaseSteps.pathContainsValues(path, dataTable);
  }

  @And("^the user received one value in path \"([^\"]*)\" and sets session variable with this name \"([^\"]*)\"$")
  public void theUserReceivedOneValueInPathAndSetsSessionVariableWithThisName(String path, String sessionVariable) {
    BaseSteps.saveValueInPathToSessionVariable(path, sessionVariable);
  }

  @And("^the response contains the following values:$")
  public void theResponseContainsTheFollowingValues(DataTable dataTable) {
    BaseSteps.responseContainsValues(dataTable);
  }

  @And("^the array path \"([^\"]*)\" at index \"([^\"]*)\" contains following data:$")
  public void theArrayPathAtIndexContainsFollowingData(String path, Integer idx, DataTable dataTable) throws Throwable {
    BaseSteps.valuesOfPathArrayAtIndex(path, idx, dataTable);
  }
}
