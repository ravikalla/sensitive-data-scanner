package com.example.scanner;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

public class ScanSteps {
    private final FileScannerService scannerService = new FileScannerService();
    private Mono<Boolean> result;

    @Given("a file containing sensitive data")
    public void a_file_with_sensitive_data() {
        String content = "hello SSN123";
        result = scannerService.scanFile(Mono.just(java.nio.ByteBuffer.wrap(content.getBytes())));
    }

    @When("the file is scanned")
    public void the_file_is_scanned() {
        // scanning started in Given
    }

    @Then("the scan reports sensitive data found")
    public void the_scan_reports_sensitive_data_found() {
        assertTrue(result.block());
    }
}
