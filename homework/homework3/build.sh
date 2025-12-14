#!/bin/bash

mvn clean package -f file-service/pom.xml
mvn clean package -f analysis-service/pom.xml
mvn clean package -f api-gateway/pom.xml
chmod +x uploader.sh checker.sh
