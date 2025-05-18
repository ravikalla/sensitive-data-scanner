package com.scansentry.repository;

import com.scansentry.model.ScanResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryScanResultRepository implements ScanResultRepository {

    private final Collection<ScanResult> store = new ConcurrentLinkedQueue<>();

    @Override
    public Mono<Void> save(ScanResult result) {
        store.add(result);
        return Mono.empty();
    }

    @Override
    public Flux<ScanResult> findAll() {
        return Flux.fromIterable(store);
    }
}