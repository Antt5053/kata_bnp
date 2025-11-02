@integration
Feature: Full CRUD flow

  Scenario: Create, retrieve, update, and delete a booking
    Given I create a new booking "createBooking.json"
    Then the response should match the booking schema
    And the booking details should match the created data
    When I retrieve the booking by ID
    Then the booking details should match the created data
    When I update the booking with new data
    Then the booking details should reflect the update
    When I partialy update the booking with new data
    Then the booking details should reflect the partial update
    When I delete the booking
    Then the booking should no longer exist