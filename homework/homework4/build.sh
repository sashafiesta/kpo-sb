#!/bin/bash

mvn clean package -f order-service/pom.xml -DskipTests
mvn clean package -f payment-service/pom.xml -DskipTests
mvn clean package -f api-gateway/pom.xml -DskipTests
chmod +x run.sh checker.sh
