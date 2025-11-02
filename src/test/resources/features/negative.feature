@negative
Feature: Invalid data

  Scenario: Create a booking with wrong values
    Given I create a new booking "wrongValues.json"
    Then the response status code should be 400

  Scenario: Create a booking with checkout before checkin
    Given I create a new booking "checkoutBeforeCheckin.json"
    Then the response status code should be 400