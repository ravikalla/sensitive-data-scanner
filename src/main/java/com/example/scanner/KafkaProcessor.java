package com.example.scanner;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class KafkaProcessor {
    private final ReactiveKafkaConsumerTemplate<String, String> consumer;
    private final ReactiveKafkaProducerTemplate<String, String> producer;
    private final S3FileService s3FileService;
    private final FileScannerService scannerService;

    public KafkaProcessor(ReactiveKafkaConsumerTemplate<String, String> consumer,
                          ReactiveKafkaProducerTemplate<String, String> producer,
                          S3FileService s3FileService,
                          FileScannerService scannerService) {
        this.consumer = consumer;
        this.producer = producer;
        this.s3FileService = s3FileService;
        this.scannerService = scannerService;
    }

    public void start(String bucket, String outputTopic) {
        consumer
                .receiveAutoAck()
                .flatMap(record -> processRecord(record, bucket, outputTopic))
                .subscribe();
    }

    private Mono<Void> processRecord(ConsumerRecord<String, String> record, String bucket, String outputTopic) {
        String key = record.value();
        return s3FileService.readFile(bucket, key)
                .as(scannerService::scanFile)
                .flatMap(found -> {
                    String message = key + ":" + (found ? "FOUND" : "NONE");
                    return producer.send(Mono.just(new ProducerRecord<>(outputTopic, key, message))).then();
                });
    }
}
