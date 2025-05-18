Feature: Scan file for sensitive data
  Scenario: File contains sensitive data
    Given a file containing sensitive data
    When the file is scanned
    Then the scan reports sensitive data found
