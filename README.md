# Sensitive Data Scanner

This project aims to build a reactive application using **Spring WebFlux** to detect sensitive information stored in Amazon S3 buckets. The vision is to run the scanner across multiple machines so that scanning tasks can be performed in parallel.

## Goals

- Use Spring's reactive programming model (WebFlux) for non-blocking processing.
- Enable distributed execution so several machines can cooperate to scan large S3 datasets concurrently.
- Identify files or objects in S3 that may contain sensitive data.

The repository is currently in an early stage and will evolve as the implementation progresses.
