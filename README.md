Resale Automation Project

Project Overview

The Resale Automation Project is a test automation framework designed to test a web application. 

Built with Selenium WebDriver, Java, Cucumber, and JUnit, it follows the Behavior-Driven Development (BDD) approach, enabling easy-to-read tests in natural language. This makes the framework accessible to both technical and non-technical team members. Logging is implemented using Log4j for enhanced traceability of test executions.


Technologies Used:-

Selenium WebDriver: Automates browser actions.

Java: Primary programming language for the test framework.

Cucumber: Enables tests to be written in Gherkin syntax (BDD).

JUnit: Manages and runs the tests.

Log4j: Provides logging functionality for better debugging and traceability.

Maven: Manages dependencies and builds the project.
Prerequisites
Before you can run the project, ensure the following tools are installed:

Java (JDK 11 or higher)

Maven: For building and managing dependencies

Eclipse IDE or another Java-compatible IDE

Cucumber plugin: If using Eclipse or IntelliJ

Browser: Chrome, Firefox, or another WebDriver-supported browser

WebDriver Executables: ChromeDriver for Chrome, or GeckoDriver for Firefox
Key Project Structure

Step Definitions:

src/test/java/stepDefinitions/ui/SearchPageSteps.java: Contains Cucumber step definition classes for UI-related steps.
src/test/java/stepDefinitions/support/ui/Hooks.java: Contains setup and teardown hooks for the test scenarios.
Test Runner:

src/test/java/runner/webRunner/TestUIRunner.java: Executes the test suite for UI tests.
Page Object Classes:

src/main/java/com/ui/pages: Follows the Page Object Model (POM) pattern, representing pages of the web application.
Feature Files:

src/test/resources/features/UI/Resale.feature: Cucumber feature file that describes test scenarios in Gherkin syntax.
Configuration and Resources:

src/main/resources/properties/config.properties: Holds configuration properties used across the framework.

src/main/resources/car_output.txt: Sample data file in CSV format used for test data validation.
Test Reports:

target/HtmlReport/HtmlReport.html: Generated HTML report displaying test execution results.