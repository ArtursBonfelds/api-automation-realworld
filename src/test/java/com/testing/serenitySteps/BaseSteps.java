package com.testing.serenitySteps;

import com.testing.requestBodies.BaseRequestBody;
import cucumber.api.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.decorators.request.RequestSpecificationDecorated;
import net.thucydides.core.annotations.Step;

import static com.tools.support.SupportVariables.SPLIT_PATTERN;
import static net.serenitybdd.core.Serenity.*;
import static net.serenitybdd.rest.SerenityRest.rest;

import com.tools.support.Service;

import org.apache.commons.collections.MapUtils;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unchecked")
public class BaseSteps {
  public static final String GET = "GET";
  public static final String PUT = "PUT";
  public static final String POST = "POST";
  public static final String PATCH = "PATCH";
  public static final String DELETE = "DELETE";
  public static final String RESPONSE = "response";
  public static final String RANDOM_EMAIL = "RANDOM_EMAIL";
  public static final String RANDOM_USERNAME = "RANDOM_USERNAME";
  public static final String DATA_PROPERTIES = "src/main/java/resources/data.properties";
  public static final Properties prop = new Properties();
  public static RequestSpecification requestSpecification = rest();

  @Step
  public static void sendRequest(String method, String endpoint) {
    setSessionVariable("REQUEST-HEADERS").to(((RequestSpecificationDecorated) requestSpecification).getHeaders().toString());
    setSessionVariable("REQUEST-METHOD").to(method);
    setSessionVariable("REQUEST-URL").to(RestAssured.baseURI + endpoint);

    Response response = Service.sendRequest(requestSpecification, method, endpoint)
            .then()
            .extract()
            .response();
    setSessionVariable(RESPONSE).to(response);
  }

  @Step
  public static void assertStatusCode(int expectedStatusCode) {
    Response res = sessionVariableCalled(RESPONSE);

    if (res.getStatusCode() != expectedStatusCode) {
      System.err.println(" Response Body:");
      res.getBody().prettyPrint();
    }

    Assert.assertEquals("status code doesn't match," +
            "\nRequest:\nHeaders: " + sessionVariableCalled("REQUEST-HEADERS") +
            "\nMethod: " + sessionVariableCalled("REQUEST-METHOD") +
            "\nUrl: " + sessionVariableCalled("REQUEST-URL") +
            "\nResponse:\nHeaders: " + res.getHeaders() +
            "\n ~Name: " + "~" + "~\n", expectedStatusCode, res.getStatusCode());
  }

  public static void setHeaders() throws IOException {
    RequestSpecification localRequestSpecification = rest();
    HashMap<String, String> hashMap = new HashMap<String, String>();
    prop.load(new FileInputStream(DATA_PROPERTIES));

    if(hasASessionVariableCalled("token")) {
      hashMap.put("Authorization", "Token " + sessionVariableCalled("token"));
    }

    hashMap.put("Accept", "application/json");
    hashMap.put("Content-Type", "application/json");
    localRequestSpecification.headers(hashMap);
    requestSpecification = localRequestSpecification;
  }

  @Step
  public static void sendRequestWithBodyJson(String method, String endpoint, String body) throws IOException {
    Map<String, Object> bodyMap = new JSONObject(body).toMap();

    System.out.println("----------------------------------");
    System.out.println("METHOD..: " + method);
    System.out.println("ENDPOINT: " + endpoint);
    MapUtils.debugPrint(System.out, "JSON", bodyMap);
    System.out.println("----------------------------------");

    setHeaders();
    requestSpecification.body(bodyMap);
    sendRequest(method, endpoint);
  }

  @Step
  public static void valueOfPathIs(String path, String expectedValue) {
    Map<String, Object> responseData = ((Response) sessionVariableCalled(RESPONSE)).jsonPath().get();
    validateValue(path, expectedValue, responseData);
  }

  private static void validateValue(String path, String expectedValue, Map<String, Object> data){
    if(expectedValue.equals(RANDOM_EMAIL)){
      expectedValue = sessionVariableCalled(RANDOM_EMAIL);
    }

    for (String key: path.replaceAll("\\s+","").split(SPLIT_PATTERN)) {
      Assert.assertNotNull(key + " not found in response body", data.get(key));
      if(data.get(key).getClass().equals(String.class)){
        Assert.assertEquals("Value of " + key + " - ", expectedValue, data.get(key).toString());
        break;
      }
      if(data.get(key).getClass().equals(Integer.class)){
        Assert.assertEquals("Value of " + key + " - ", Integer.parseInt(expectedValue), data.get(key));
        break;
      }
      if(data.get(key).getClass().equals(Float.class)){
        Assert.assertEquals("Value of " + key + " - ", Float.parseFloat(expectedValue), data.get(key));
        break;
      }
      data = (Map<String, Object>) data.get(key);
    }
  }

  @Step
  public static void pathContainsValues(String path, DataTable dataTable){
    Map<String, Object> expectedData = new HashMap<>(dataTable.asMap(String.class, Object.class));
    Map<String, Object> responseData = ((Response) sessionVariableCalled(RESPONSE)).jsonPath().get();

    validateValues(path, expectedData, responseData);
  }

  private static void validateValues(String path, Map<String, Object> expectedData, Map<String, Object> responseData) {
    if(expectedData.get("email") != null && expectedData.get("email").equals(RANDOM_EMAIL)){
      expectedData.replace("email", sessionVariableCalled(RANDOM_EMAIL));
    }

    if(expectedData.get("username") != null && expectedData.get("username").equals(RANDOM_USERNAME)){
      expectedData.replace("username", sessionVariableCalled(RANDOM_USERNAME));
    }

    for (String key: path.replaceAll("\\s+","").split(SPLIT_PATTERN)) {
      Assert.assertNotNull(key + " not found in response data", responseData.get(key));
      responseData = (Map<String, Object>) responseData.get(key);
    }

    for (String key : expectedData.keySet()) {
      Assert.assertNotNull(key + " not found in response data", responseData.get(key));
      Assert.assertEquals("Value of " + key + " - ", expectedData.get(key), responseData.get(key).toString());
    }
  }

  @Step
  public static void saveValueInPathToSessionVariable(String path, String sessionVariable) {
    Map<String, Object> responseData = ((Response) sessionVariableCalled(RESPONSE)).jsonPath().get();

    for (String key: path.replaceAll("\\s+","").split(SPLIT_PATTERN)) {
      Assert.assertNotNull(key + " not found in response body", responseData.get(key));
      if(responseData.get(key).getClass().equals(String.class)){
        setSessionVariable(sessionVariable).to(responseData.get(key).toString());
        break;
      }
      if(responseData.get(key).getClass().equals(Integer.class)){
        setSessionVariable(sessionVariable).to(responseData.get(key));
        break;
      }
      responseData = (Map<String, Object>) responseData.get(key);
    }
  }

  @Step
  public static void responseContainsValues(DataTable dataTable){
    Map<String, Object> expectedData = dataTable.asMap(String.class, Object.class);
    Map<String, Object> responseData = ((Response) sessionVariableCalled(RESPONSE)).jsonPath().get();

    for (String key : expectedData.keySet()) {
      Assert.assertNotNull(key + " not found in response data", responseData.get(key));
      Assert.assertEquals("Value of " + key + " - ", expectedData.get(key), responseData.get(key).toString());
    }
  }

  @Step
  public static String createBody(DataTable dataTable){
    Map<String, Object> requestData = new HashMap<>(dataTable.asMap(String.class, Object.class));
    BaseRequestBody baseRequestBody = new BaseRequestBody();

    for (String key : requestData.keySet()) {
      baseRequestBody.addKey(key, requestData.get(key));
    }
    return baseRequestBody.getBody();
  }

  @Step
  public static String createBody(Map<String, Object> requestData){
    BaseRequestBody baseRequestBody = new BaseRequestBody();

    for (String key : requestData.keySet()) {
      baseRequestBody.addKey(key, requestData.get(key));
    }
    return baseRequestBody.getBody();
  }

  @Step
  public static BaseRequestBody createBodyCustom(DataTable dataTable){
    Map<String, Object> requestData = new HashMap<>(dataTable.asMap(String.class, Object.class));
    BaseRequestBody baseRequestBody = new BaseRequestBody();

    for (String key : requestData.keySet()) {
      baseRequestBody.addKey(key, requestData.get(key));
    }
    return baseRequestBody;
  }

  @Step
  public static void valuesOfPathArrayAtIndex(String path, Integer idx, DataTable dataTable) {
    Map<String, Object> responseData = ((Response) sessionVariableCalled(RESPONSE)).jsonPath().get();

    for (String key: path.replaceAll("\\s+","").split(SPLIT_PATTERN)) {
      Assert.assertNotNull(key + " not found in response body", responseData.get(key));
      if(responseData.get(key).getClass().equals(ArrayList.class)){
        Assert.assertNotNull(key + " not found in response data", responseData.get(key));
        Map<String, Object> arrayData = ((ArrayList<Map<String, Object>>) responseData.get(key)).get(idx);
        Map<String, Object> expectedData = dataTable.asMap(String.class, Object.class);
        for (String exKey : expectedData.keySet()) {
          validateValue(exKey, expectedData.get(exKey).toString(), arrayData);
        }
        return;
      }
      responseData = (Map<String, Object>) responseData.get(key);
    }
    throw new Error("Failed to find Array in given path: " + path);
  }
}
