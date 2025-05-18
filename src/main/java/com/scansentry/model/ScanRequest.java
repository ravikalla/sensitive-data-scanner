package com.scansentry.model;

import lombok.Data;

@Data
public class ScanRequest {
    private String bucket;
    private String objectKey;
    private long offsetStart;
    private long offsetEnd;
    private String content;

    // Getters and Setters
}
