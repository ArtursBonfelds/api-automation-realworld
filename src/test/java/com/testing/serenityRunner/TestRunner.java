package com.testing.serenityRunner;

import cucumber.api.CucumberOptions;
import io.restassured.RestAssured;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/"},
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-report.json"},
        glue = {"com.testing.gherkinsDefinitions"}
)
public class TestRunner {
  private final static Integer PORT = 7000;
  private final static Integer TIMEOUT = 45;
  private final static String URL_LOCAL = "http://localhost:" + PORT;
  private final static String CHECK_IF_PORT_IS_USED_COMMAND = "lsof -P | grep ':" + PORT + "'| grep LISTEN";

  // Server related variables
  private final static String SERVER_NAME = "kotlin-javalin-realworld-example-app";
  private final static String SERVER_STARTER = "./gradlew run";
  private final static String SERVER_KILLING_COMMAND = "./gradlew --stop";
  private final static String SERVER_PROCESS_STARTER_COMMAND = "cd ../" + SERVER_NAME + ";" + SERVER_STARTER;
  private final static String SERVER_PROCESS_KILLING_COMMAND = "cd ../" + SERVER_NAME + ";" + SERVER_KILLING_COMMAND;

  private TestRunner() {
  }

  @BeforeClass
  public static void setUp() throws IOException, InterruptedException {
    RestAssured.baseURI = URL_LOCAL;
    stopServer();
    startServer();
  }

  @AfterClass
  public static void cleanUp() throws IOException, InterruptedException {
    // stopServer();
  }

  private static void startServer() throws IOException {
    new ProcessBuilder().command("bash", "-c", SERVER_PROCESS_STARTER_COMMAND).start();
    long start = System.currentTimeMillis();

    while (!isServerRunning()){
      if(System.currentTimeMillis() - start > TIMEOUT * 1000){
        throw new Error("Server starting took longer than " + TIMEOUT);
      }
    }
  }

  private static void stopServer() throws IOException, InterruptedException {
    new ProcessBuilder().command("bash", "-c", SERVER_PROCESS_KILLING_COMMAND).start();
    if(isServerRunning()){
      sleep(5000);
    }
  }

  private static boolean isServerRunning() throws IOException {
    Process checkIfPortIsUsed;
    checkIfPortIsUsed = new ProcessBuilder().command("bash", "-c", CHECK_IF_PORT_IS_USED_COMMAND).start();
    return new BufferedReader(new InputStreamReader(checkIfPortIsUsed.getInputStream())).readLine() != null;
  }
}
