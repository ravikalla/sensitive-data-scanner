# S3 Sensitive Data Scanner

An open-source project to scan an organization's entire Amazon S3 storage (\~100PB) for sensitive data types like PII, secrets, and credentials—efficiently and cost-effectively within 1 year.

---

## 🚀 Key Features

* Scan 100PB+ S3 data using scalable and modular architecture
* Push metadata to Kafka for distributed processing
* Horizontal scaling with reactive Spring-based services
* Detection using regex, ML/NLP, or LLM-based plugins
* Visual dashboards for insights and trends
* Cost optimization strategies (spot instances, Range GETs, Bloom filters)

---

## 📐 Architecture Diagram

```mermaid
graph TD
  A[S3 Buckets] --> B[Metadata Scanner]
  B --> C[Kafka Topic - File Metadata]
  C --> D[DataReader - Horizontally Scalable]
  D --> E[Chunked File Content]
  E --> F[ScanSentry - Worker Nodes]
  F --> G[Scan Results]
  G --> H[Kafka Topic - Scan Output]
  G --> I[Dashboard + Reports]
```

---

## 🧱 System Components

### Metadata Scanner

* Periodically scans S3 Inventory or ListObjectsV2
* Extracts object metadata
* Publishes to Kafka

### DataReader

* Horizontally scalable consumers
* Reads file chunks using S3 Range GETs
* Parses common file formats

### ScanSentry

* Regex and ML-based data detectors
* LLM plugin support (optional)
* Outputs scan results to Kafka

### Dashboard

* Powered by Kibana or Grafana
* Real-time trends and coverage stats

---

## 📦 Tech Stack

* Java + Spring WebFlux (Reactive)
* Apache Kafka
* AWS SDK v2
* Elasticsearch / Clickhouse
* Kibana / Grafana
* Optional Python plugins for ML/LLM

---

## 💰 Cost Optimizations

* Spot instances
* Avoid full downloads (Range GET)
* Sampling and risk prioritization
* Bloom filters to skip known-safe files

---

## 📈 Output Format

```json
{
  "bucket": "prod-data-archive",
  "objectKey": "2021/user_data.csv",
  "offsetRange": "10456-10575",
  "sensitiveType": "SSN",
  "sample": "***-**-1234"
}
```

---

## 🛠️ Getting Started (Planned)

> 🚧 This section will be populated as components are implemented.

* [ ] Setup Kafka + Topics
* [ ] Implement Metadata Scanner
* [ ] Build DataReader
* [ ] Develop ScanSentry engine
* [ ] Integrate dashboards

### Standalone Kafka Scanner

The `KafkaScannerApplication` demonstrates a minimal setup that continuously reads
messages from a Kafka topic, scans them for sensitive data, and stores results in
an in-memory repository.

Run it after building the project:

```bash
./gradlew build
java -cp build/classes/java/main com.scansentry.KafkaScannerApplication
```

The application reads Kafka connection details from the environment variables
`KAFKA_BOOTSTRAP_SERVERS` and `KAFKA_TOPIC` (command line arguments override
them).

### Creating a Kafka topic locally

Build the helper Docker image and run it with the appropriate environment
variables to create the topic:

```bash
docker build -f KafkaTopic.Dockerfile -t kafka-topic-helper .
docker run --rm \
  -e KAFKA_BOOTSTRAP_SERVERS=localhost:9092 \
  -e KAFKA_TOPIC=scan-input \
  kafka-topic-helper
```

The image uses the same `KAFKA_BOOTSTRAP_SERVERS` and `KAFKA_TOPIC` variables
(with optional `KAFKA_PARTITIONS` and `KAFKA_REPLICATION`) to create the topic.

---

## 📅 Development Timeline

| Week | Milestone                       |
| ---- | ------------------------------- |
| 1    | Metadata Scanner + Kafka topics |
| 2    | DataReader with chunking        |
| 3    | ScanSentry detection logic      |
| 4    | Dashboards + GitHub polish      |

---

## 🙌 Contributing

This project is designed to showcase scalable architecture and system design. Contributions welcome via pull requests, issue reports, or plugin ideas.

---

## 🧠 Author

ravikalla – [GitHub](http://github.com/ravikalla) | [LinkedIn](https://www.linkedin.com/in/ravikalla/)

---

## 📄 License

MIT License
