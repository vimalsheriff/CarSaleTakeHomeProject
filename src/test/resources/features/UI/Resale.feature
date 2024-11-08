@UI
Feature: Resale Car validations

  @Resale
  Scenario: Car validations for re-sale cars
    Given user launches the we buy any car website
    And user search the car details using "SE06PPK" registration and mileage as "10000"
    Then verify the car details with registration Number "SE06PPK"
    And Compare the file values with car resale website


  @Resale
  Scenario: Car validations for re-sale cars
    Given user launches the we buy any car website
    And user search the car details using "TJ07KFT" registration and mileage as "10000"
    Then verify the car details with registration Number "TJ07KFT"
    And Compare the file values with car resale website


  @Resale
  Scenario: Car validations for re-sale cars
    Given user launches the we buy any car website
    And user search the car details using "FD66YXH" registration and mileage as "15000"
    Then verify the car details with registration Number "FD66YXH"
    And Compare the file values with car resale website


  @Resale
  Scenario: Car validations for re-sale cars
    Given user launches the we buy any car website
    And user search the car details using "PF59FBC" registration and mileage as "16000"
    Then verify the car details with registration Number "PF59FBC"
    And Compare the file values with car resale website



