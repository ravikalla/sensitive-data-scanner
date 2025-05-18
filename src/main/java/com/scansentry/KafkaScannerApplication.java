package com.scansentry;

import com.scansentry.detector.RegexDetector;
import com.scansentry.detector.SensitiveDataDetector;
import com.scansentry.repository.InMemoryScanResultRepository;
import com.scansentry.repository.ScanResultRepository;
import com.scansentry.service.KafkaScanService;

public class KafkaScannerApplication {
    public static void main(String[] args) throws Exception {
        String bootstrapServers = System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092");
        String topic = System.getenv().getOrDefault("KAFKA_TOPIC", "scan-input");
        if (args.length > 0) {
            bootstrapServers = args[0];
        }
        if (args.length > 1) {
            topic = args[1];
        }

        SensitiveDataDetector detector = new RegexDetector();
        ScanResultRepository repository = new InMemoryScanResultRepository();
        KafkaScanService service = new KafkaScanService(bootstrapServers, topic, detector, repository);

        service.start()
               .doOnNext(result -> System.out.println("Detected: " + result))
               .subscribe();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Stopping Kafka scanner...")));
        // Keep running indefinitely
        while (true) {
            Thread.sleep(1000);
        }
    }
}