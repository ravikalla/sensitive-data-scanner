package com.scansentry.repository;

import com.scansentry.model.ScanResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ScanResultRepository {
    Mono<Void> save(ScanResult result);
    Flux<ScanResult> findAll();
}