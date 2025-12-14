#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <submission_id>"
    exit 1
fi

curl -s http://localhost:8080/api/v1/reports/$1

echo ""
