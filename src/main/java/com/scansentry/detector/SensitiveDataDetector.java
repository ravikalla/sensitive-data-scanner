package com.scansentry.detector;

import com.scansentry.model.ScanRequest;
import com.scansentry.model.ScanResult;

import java.util.Optional;

public interface SensitiveDataDetector {
    Optional<ScanResult> detect(ScanRequest request);
}
