#!/bin/bash

if [ "$#" -ne 3 ]; then
    echo "Usage: $0 <file_path> <student_name> <task_id>"
    exit 1
fi

curl -X POST http://localhost:8080/api/v1/files/upload \
  -F "file=@$1" \
  -F "student_name=$2" \
  -F "task_id=$3"

echo ""
