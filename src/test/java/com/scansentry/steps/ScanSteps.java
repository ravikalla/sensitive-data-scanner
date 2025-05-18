package com.scansentry.steps;

import com.scansentry.detector.RegexDetector;
import com.scansentry.model.ScanRequest;
import com.scansentry.model.ScanResult;
import io.cucumber.java.en.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ScanSteps {
    private ScanRequest request;
    private Optional<ScanResult> result;

    @Given("a scan request with content {string}")
    public void a_scan_request_with_content(String content) {
        request = new ScanRequest();
        request.setBucket("test-bucket");
        request.setObjectKey("test-file.txt");
        request.setOffsetStart(0);
        request.setOffsetEnd(100);
        request.setContent(content);
    }

    @When("the scan is performed")
    public void the_scan_is_performed() {
        RegexDetector detector = new RegexDetector();
        result = detector.detect(request);
    }

    @Then("the result should include {string}")
    public void the_result_should_include(String type) {
        assertTrue(result.isPresent());
        assertEquals(type, result.get().getSensitiveType());
    }
}
