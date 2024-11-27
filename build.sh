#!/bin/bash

# Clean previous builds
./mvnw clean

# Update dependencies
./mvnw dependency:resolve

# Build the project
./mvnw clean install -DskipTests

# Run the application
./mvnw spring-boot:run 