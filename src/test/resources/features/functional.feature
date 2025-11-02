@functional
Feature: Test API

  Scenario: Get booking ids
    Given the API endpoint is "/booking"
    When I send a GET request
    Then the response status code should be 200

  Scenario: Get health check
    Given the API endpoint is "/ping"
    When I send a GET request
    Then the response status code should be 201

  Scenario: Get booking by id
    Given a booking already exist
    When I retrieve the booking by ID
    Then the booking details should match the created data

  Scenario: Update booking
    Given a booking already exist
    When I update the booking with new data    
    Then the booking details should reflect the update
    And the response status code should be 200

  Scenario: Delete a booking
    Given a booking already exist
    When I delete the booking
    Then the booking should no longer exist


