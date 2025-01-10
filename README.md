```
curl -X 'POST' \
  'http://localhost:9191/api/orders' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 103,
  "productId": 1,
  "quantity": 5
}'
```
