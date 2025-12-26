#!/bin/bash
echo "Server is going to be available at http://localhost:8080"
docker compose down
sleep 5
docker compose up --build