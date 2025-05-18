package com.scansentry.detector;

import com.scansentry.model.ScanRequest;
import com.scansentry.model.ScanResult;

import java.util.*;
import java.util.regex.*;

public class RegexDetector implements SensitiveDataDetector {

    private static final Map<String, Pattern> patterns = Map.of(
        "SSN", Pattern.compile("\\b\\d{3}-\\d{2}-\\d{4}\\b"),
        "CREDIT_CARD", Pattern.compile("\\b(?:\\d[ -]*?){13,16}\\b"),
        "EMAIL", Pattern.compile("\\b[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,6}\\b")
    );

    @Override
    public Optional<ScanResult> detect(ScanRequest request) {
        for (Map.Entry<String, Pattern> entry : patterns.entrySet()) {
            Matcher matcher = entry.getValue().matcher(request.getContent());
            if (matcher.find()) {
                String match = matcher.group();
                String redacted = match.replaceAll("[0-9A-Za-z]", "*");
                ScanResult result = new ScanResult();
                result.setBucket(request.getBucket());
                result.setObjectKey(request.getObjectKey());
                result.setOffsetRange(request.getOffsetStart() + "-" + request.getOffsetEnd());
                result.setSensitiveType(entry.getKey());
                result.setSample(redacted);
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }
}
