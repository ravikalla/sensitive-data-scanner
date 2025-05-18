Feature: ScanSentry detects sensitive data

  Scenario: Detect SSN in content
    Given a scan request with content "My SSN is 123-45-6789"
    When the scan is performed
    Then the result should include "SSN"
