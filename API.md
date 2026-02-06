# 📡 API Documentation

## Base URL

```
Development: http://localhost:8080/api
Production: https://your-domain.com/api
```

## Authentication

### API Authentication

Sử dụng HTTP Basic Auth hoặc Session-based auth từ web login.

Headers:

```
Authorization: Basic base64(username:password)
```

Hoặc login qua web trước, sau đó API sẽ sử dụng session cookie.

## Response Format

### Success Response

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "metadata": {
    "page": 0,
    "pageSize": 10,
    "totalElements": 100,
    "totalPages": 10,
    "requestId": "uuid",
    "executionTime": 123
  }
}
```

### Error Response

```json
{
  "success": false,
  "message": "Error message",
  "status": 400,
  "error": "Bad Request",
  "errors": ["Validation error 1", "Validation error 2"]
}
```

## Books API

### Get All Books

```http
GET /api/books?page=0&size=10&sortBy=title&sortDir=ASC
```

**Query Parameters:**

- `page` (integer, optional): Page number (default: 0)
- `size` (integer, optional): Page size (default: 10)
- `sortBy` (string, optional): Sort field (default: "id")
- `sortDir` (string, optional): Sort direction ASC/DESC (default: "DESC")

**Response:**

```json
{
  "success": true,
  "data": {
    "items": [
      {
        "id": 1,
        "title": "Spring Boot in Action",
        "author": "Craig Walls",
        "price": 29.99,
        "description": "A comprehensive guide to Spring Boot",
        "stock": 50,
        "imageUrl": "https://example.com/image.jpg",
        "category": {
          "id": 1,
          "name": "Programming"
        }
      }
    ],
    "page": 0,
    "pageSize": 10,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

### Get Book by ID

```http
GET /api/books/{id}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "title": "Spring Boot in Action",
    "author": "Craig Walls",
    "price": 29.99,
    "description": "A comprehensive guide to Spring Boot",
    "stock": 50,
    "imageUrl": "https://example.com/image.jpg",
    "category": {
      "id": 1,
      "name": "Programming"
    }
  }
}
```

### Create Book (Admin only)

```http
POST /api/books
Content-Type: application/json
```

**Request Body:**

```json
{
  "title": "Spring Boot in Action",
  "author": "Craig Walls",
  "price": 29.99,
  "description": "A comprehensive guide to Spring Boot",
  "stock": 50,
  "imageUrl": "https://example.com/image.jpg",
  "categoryId": 1
}
```

**Validation Rules:**

- `title`: Required, not blank
- `author`: Required, not blank
- `price`: Required, must be positive
- `categoryId`: Required, must exist

**Response:**

```json
{
  "success": true,
  "message": "Tạo sách mới thành công",
  "data": { ... }
}
```

### Update Book (Admin only)

```http
PUT /api/books/{id}
Content-Type: application/json
```

**Request Body:** Same as Create Book

**Response:**

```json
{
  "success": true,
  "message": "Cập nhật sách thành công",
  "data": { ... }
}
```

### Delete Book (Admin only)

```http
DELETE /api/books/{id}
```

**Response:**

```json
{
  "success": true,
  "message": "Xóa sách thành công"
}
```

### Search Books

```http
GET /api/books/search?keyword=spring
```

**Query Parameters:**

- `keyword` (string, required): Search keyword

**Response:** Same as Get All Books

## Categories API

### Get All Categories

```http
GET /api/categories
```

**Response:**

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Programming"
    },
    {
      "id": 2,
      "name": "Science"
    }
  ]
}
```

### Get Category by ID

```http
GET /api/categories/{id}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Programming",
    "books": [
      {
        "id": 1,
        "title": "Spring Boot in Action",
        "author": "Craig Walls",
        "price": 29.99
      }
    ]
  }
}
```

### Create Category (Admin only)

```http
POST /api/categories
Content-Type: application/json
```

**Request Body:**

```json
{
  "name": "New Category"
}
```

**Response:**

```json
{
  "success": true,
  "message": "Tạo danh mục mới thành công",
  "data": { ... }
}
```

### Update Category (Admin only)

```http
PUT /api/categories/{id}
Content-Type: application/json
```

**Request Body:**

```json
{
  "name": "Updated Category"
}
```

**Response:**

```json
{
  "success": true,
  "message": "Cập nhật danh mục thành công",
  "data": { ... }
}
```

### Delete Category (Admin only)

```http
DELETE /api/categories/{id}
```

**Response:**

```json
{
  "success": true,
  "message": "Xóa danh mục thành công"
}
```

## Error Codes

| Status Code | Description                             |
| ----------- | --------------------------------------- |
| 200         | OK - Request successful                 |
| 201         | Created - Resource created successfully |
| 400         | Bad Request - Invalid input             |
| 401         | Unauthorized - Authentication required  |
| 403         | Forbidden - Insufficient permissions    |
| 404         | Not Found - Resource not found          |
| 409         | Conflict - Resource conflict            |
| 500         | Internal Server Error - Server error    |

## Rate Limiting

API endpoints có thể bị rate limit để tránh abuse:

- 100 requests per minute cho authenticated users
- 20 requests per minute cho anonymous users

## Examples

### Using cURL

#### Get all books

```bash
curl -X GET "http://localhost:8080/api/books?page=0&size=10" \
  -H "Accept: application/json"
```

#### Create a book (with authentication)

```bash
curl -X POST "http://localhost:8080/api/books" \
  -u "admin:admin123" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Book",
    "author": "John Doe",
    "price": 19.99,
    "categoryId": 1
  }'
```

### Using JavaScript (Fetch API)

#### Get all books

```javascript
fetch("http://localhost:8080/api/books")
  .then((response) => response.json())
  .then((data) => console.log(data))
  .catch((error) => console.error("Error:", error));
```

#### Create a book

```javascript
fetch("http://localhost:8080/api/books", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
    Authorization: "Basic " + btoa("admin:admin123"),
  },
  body: JSON.stringify({
    title: "New Book",
    author: "John Doe",
    price: 19.99,
    categoryId: 1,
  }),
})
  .then((response) => response.json())
  .then((data) => console.log(data))
  .catch((error) => console.error("Error:", error));
```

### Using Python (Requests)

```python
import requests

# Get all books
response = requests.get('http://localhost:8080/api/books')
books = response.json()
print(books)

# Create a book
auth = ('admin', 'admin123')
data = {
    'title': 'New Book',
    'author': 'John Doe',
    'price': 19.99,
    'categoryId': 1
}
response = requests.post(
    'http://localhost:8080/api/books',
    json=data,
    auth=auth
)
result = response.json()
print(result)
```

## Testing

### Postman Collection

Import file `postman_collection.json` vào Postman để test API.

### Swagger UI (Optional)

Nếu cần Swagger UI, thêm dependencies:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

Truy cập: http://localhost:8080/swagger-ui.html

## Support

Nếu có vấn đề với API, vui lòng tạo issue trên GitHub hoặc liên hệ:

- Email: ducminh@hutech.edu.vn
- GitHub Issues: [Link]
