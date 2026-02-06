# 🔧 Hướng dẫn phát triển

## Cấu trúc Code

### Entities

- Sử dụng Lombok để giảm boilerplate code
- Tất cả entities đều có `@PrePersist` và `@PreUpdate` cho audit
- Sử dụng `@Builder.Default` cho các field có giá trị khởi tạo
- Lazy loading cho relationships để tối ưu performance

### Services

- Tất cả services đều có `@Transactional`
- Isolation level: `SERIALIZABLE` để đảm bảo data consistency
- Logging với SLF4J
- Validation ở tầng service
- Exception handling với custom exceptions

### Controllers

- Web controllers: Trả về view name
- API controllers: Sử dụng `@RestController` và trả về JSON
- Validation với `@Valid`
- Error handling với flash attributes cho web
- Global exception handler cho API

### Repositories

- Extend `JpaRepository`
- Custom queries với `@Query`
- Method naming convention của Spring Data JPA
- Optimized queries với fetch joins

## Quy tắc code

### Naming Convention

- Class: PascalCase
- Method/Variable: camelCase
- Constants: UPPER_SNAKE_CASE
- Package: lowercase

### Code Style

- Indentation: 4 spaces
- Line length: Max 120 characters
- Braces: Same line for opening brace
- Comments: Tiếng Việt cho business logic, English cho technical

### Git Commit Messages

```
<type>(<scope>): <subject>

<body>

<footer>
```

Types:

- `feat`: Tính năng mới
- `fix`: Sửa lỗi
- `docs`: Cập nhật documentation
- `style`: Format code (không ảnh hưởng logic)
- `refactor`: Refactor code
- `test`: Thêm/sửa tests
- `chore`: Các task khác (build, dependencies, etc.)

Ví dụ:

```
feat(book): thêm tính năng tìm kiếm sách

- Thêm search endpoint
- Thêm search UI
- Thêm unit tests

Closes #123
```

## Testing

### Unit Tests

```java
@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private IBookRepository bookRepository;

    @Test
    void testGetAllBooks() {
        // Arrange
        List<Book> expectedBooks = Arrays.asList(
            new Book(1L, "Book 1", "Author 1", 10.0),
            new Book(2L, "Book 2", "Author 2", 20.0)
        );
        when(bookRepository.findAllWithCategory()).thenReturn(expectedBooks);

        // Act
        List<Book> actualBooks = bookService.getAllBooks();

        // Assert
        assertEquals(expectedBooks.size(), actualBooks.size());
    }
}
```

### Integration Tests

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetBooks() throws Exception {
        mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(view().name("list"));
    }
}
```

## Performance Optimization

### Database

- Sử dụng indexes cho các cột thường xuyên search
- Lazy loading cho relationships
- Batch operations cho bulk inserts/updates
- Connection pooling với HikariCP

### Caching

```java
@Cacheable(value = "books", key = "#id")
public Book getBookById(Long id) {
    return bookRepository.findById(id).orElse(null);
}

@CacheEvict(value = "books", key = "#id")
public void updateBook(Long id, Book book) {
    // Update logic
}
```

### Query Optimization

```java
// BAD: N+1 problem
List<Book> books = bookRepository.findAll(); // 1 query
for (Book book : books) {
    Category category = book.getCategory(); // N queries
}

// GOOD: Fetch join
@Query("SELECT b FROM Book b LEFT JOIN FETCH b.category")
List<Book> findAllWithCategory(); // 1 query
```

## Security Best Practices

### Password Encoding

```java
// Luôn encode password trước khi lưu
String encodedPassword = passwordEncoder.encode(rawPassword);
user.setPassword(encodedPassword);
```

### SQL Injection Prevention

```java
// BAD: String concatenation
@Query("SELECT u FROM User u WHERE u.username = '" + username + "'")

// GOOD: Parameter binding
@Query("SELECT u FROM User u WHERE u.username = :username")
User findByUsername(@Param("username") String username);
```

### XSS Prevention

- Thymeleaf tự động escape HTML
- Validate input ở cả client và server
- Sử dụng Content Security Policy headers

## Deployment

### Development

```bash
./mvnw spring-boot:run
```

### Production

```bash
# Build
./mvnw clean package -DskipTests

# Run
java -jar -Dspring.profiles.active=prod target/TranDucMinh-0.0.1-SNAPSHOT.jar
```

### Environment Variables

```bash
export DB_URL=jdbc:mysql://localhost:3306/bookstore
export DB_USERNAME=root
export DB_PASSWORD=secret
export OAUTH_CLIENT_ID=your-client-id
export OAUTH_CLIENT_SECRET=your-client-secret
```

### Docker (Optional)

```dockerfile
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
docker build -t bookstore .
docker run -p 8080:8080 bookstore
```

## Monitoring và Logging

### Logging Levels

- `ERROR`: Lỗi nghiêm trọng
- `WARN`: Cảnh báo
- `INFO`: Thông tin quan trọng
- `DEBUG`: Debug information
- `TRACE`: Chi tiết nhất

### Structured Logging

```java
log.info("User {} logged in at {}", username, LocalDateTime.now());
log.error("Error processing order {}: {}", orderId, e.getMessage(), e);
```

### Health Check

```
GET /actuator/health
{
  "status": "UP",
  "components": {
    "db": { "status": "UP" },
    "diskSpace": { "status": "UP" }
  }
}
```

## Troubleshooting

### Lỗi thường gặp

#### 1. Port already in use

```bash
# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

#### 2. Database connection failed

- Kiểm tra MySQL đang chạy
- Kiểm tra credentials trong application.properties
- Kiểm tra database đã được tạo

#### 3. OAuth2 login failed

- Kiểm tra client ID và secret
- Kiểm tra redirect URI trong Google Console
- Kiểm tra internet connection

## Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)
