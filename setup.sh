#!/bin/bash

# Create base project structure
mkdir -p src/main/java/com/healthwealth
mkdir -p src/main/resources
mkdir -p src/test/java/com/healthwealth
mkdir -p src/test/resources

# Create package structure
cd src/main/java/com/healthwealth
mkdir -p common/{entity,exception,dto}
mkdir -p user/{entity,repository,service,controller}
mkdir -p health/{entity,repository,service,controller}
mkdir -p wealth/{entity,repository,service,controller}
mkdir -p notification/{service,exception}
mkdir -p security/{controller,service,dto,exception,util}
mkdir -p config

# Set permissions
cd ../../../../..
chmod +x mvnw
chmod +x mvnw.cmd

# Clean and install
./mvnw clean install -DskipTests