#!/bin/bash
set -e
/opt/bitnami/kafka/bin/kafka-topics.sh --create --if-not-exists \
  --bootstrap-server "${KAFKA_BOOTSTRAP_SERVERS:-localhost:9092}" \
  --replication-factor "${KAFKA_REPLICATION:-1}" \
  --partitions "${KAFKA_PARTITIONS:-1}" \
  --topic "${KAFKA_TOPIC:-scan-input}"
