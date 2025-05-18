package com.scansentry.model;

import lombok.Data;

@Data
public class ScanResult {
    private String bucket;
    private String objectKey;
    private String offsetRange;
    private String sensitiveType;
    private String sample;

    // Getters and Setters
}
