#!/bin/bash

BASE_URL="http://localhost:8080/api/v1"

echo "TEST 1: Successful Order (User 100)"

curl -s -X POST "$BASE_URL/payments/accounts" -H "Content-Type: application/json" -d '{"userId": 100}' > /dev/null
curl -s -X POST "$BASE_URL/payments/accounts/deposit" -H "Content-Type: application/json" -d '{"userId": 100, "amount": 1000}' > /dev/null

echo "Sending order request..."
RESPONSE=$(curl -s -X POST "$BASE_URL/orders" -H "Content-Type: application/json" -d '{"userId": 100, "amount": 500, "description": "Phone"}')
echo "Created: $RESPONSE"

ID=$(echo $RESPONSE | grep -o '"id":[0-9]*' | grep -o '[0-9]*')

echo "Waiting 10 seconds for processing..."
sleep 10

echo "Checking status (Expect FINISHED):"
curl -s "$BASE_URL/orders/$ID"
echo "" 
echo ""


echo "TEST 2: Failed Order (User 200)"

curl -s -X POST "$BASE_URL/payments/accounts" -H "Content-Type: application/json" -d '{"userId": 200}' > /dev/null
curl -s -X POST "$BASE_URL/payments/accounts/deposit" -H "Content-Type: application/json" -d '{"userId": 200, "amount": 10}' > /dev/null

echo "Sending order request..."
RESPONSE_2=$(curl -s -X POST "$BASE_URL/orders" -H "Content-Type: application/json" -d '{"userId": 200, "amount": 100, "description": "Car"}')
echo "Created: $RESPONSE_2"

ID_2=$(echo $RESPONSE_2 | grep -o '"id":[0-9]*' | grep -o '[0-9]*')

echo "Waiting 10 seconds for processing..."
sleep 10

echo "Checking status (Expect CANCELLED):"
curl -s "$BASE_URL/orders/$ID_2"
echo ""
