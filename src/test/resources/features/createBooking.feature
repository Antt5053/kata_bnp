@functional @smoke
Feature: Create booking

  Scenario: Create booking
    Given I create a new booking "createBooking.json"
    Then the response should match the booking schema
    And the response status code should be 200
    And the booking details should match the created data