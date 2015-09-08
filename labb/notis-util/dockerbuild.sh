#!/bin/bash
docker build -t sample/vertx-java-fat -f src/main/docker/Dockerfile .

# Run with docker run -t -i -p 8080:8080 sample/vertx-java-fat