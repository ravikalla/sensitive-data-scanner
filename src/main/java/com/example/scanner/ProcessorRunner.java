package com.example.scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProcessorRunner implements CommandLineRunner {

    private final KafkaProcessor processor;

    @Value("${s3.bucket}")
    private String bucket;

    @Value("${kafka.output.topic}")
    private String outputTopic;

    public ProcessorRunner(KafkaProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run(String... args) {
        processor.start(bucket, outputTopic);
    }
}
