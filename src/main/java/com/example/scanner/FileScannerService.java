package com.example.scanner;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

@Service
public class FileScannerService {
    private static final Pattern SENSITIVE_PATTERN = Pattern.compile("SSN\\d{3}");

    public Mono<Boolean> scanFile(Mono<ByteBuffer> data) {
        return data
                .map(bb -> StandardCharsets.UTF_8.decode(bb).toString())
                .any(content -> SENSITIVE_PATTERN.matcher(content).find());
    }
}
