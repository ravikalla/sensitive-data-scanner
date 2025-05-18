package com.scansentry.service;

import com.scansentry.detector.SensitiveDataDetector;
import com.scansentry.model.ScanRequest;
import com.scansentry.model.ScanResult;
import com.scansentry.repository.ScanResultRepository;
import org.apache.kafka.common.serialization.StringDeserializer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.Optional;

public class KafkaScanService {

    private final SensitiveDataDetector detector;
    private final ScanResultRepository repository;
    private final KafkaReceiver<String, String> receiver;

    public KafkaScanService(String bootstrapServers, String topic,
                            SensitiveDataDetector detector,
                            ScanResultRepository repository) {
        this.detector = detector;
        this.repository = repository;
        ReceiverOptions<String, String> options = ReceiverOptions.<String, String>create(
                Collections.singletonMap("bootstrap.servers", bootstrapServers))
                .subscription(Collections.singleton(topic))
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(new StringDeserializer());
        this.receiver = KafkaReceiver.create(options);
    }

    public Flux<ScanResult> start() {
        return receiver.receive()
                .flatMap(this::handleRecord);
    }

    private Mono<ScanResult> handleRecord(ReceiverRecord<String, String> record) {
        ScanRequest request = buildRequest(record);
        Optional<ScanResult> detection = detector.detect(request);
        record.receiverOffset().acknowledge();
        return detection
                .map(result -> repository.save(result).thenReturn(result))
                .orElse(Mono.empty());
    }

    private ScanRequest buildRequest(ReceiverRecord<String, String> record) {
        ScanRequest req = new ScanRequest();
        req.setBucket(record.key() == null ? "" : record.key());
        req.setObjectKey("kafka-message");
        req.setOffsetStart(0);
        req.setOffsetEnd(0);
        req.setContent(record.value());
        return req;
    }
}