FROM bitnami/kafka:latest
COPY docker/create-topic.sh /usr/local/bin/create-topic.sh
RUN chmod +x /usr/local/bin/create-topic.sh
ENTRYPOINT ["/usr/local/bin/create-topic.sh"]
