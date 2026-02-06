# 📚 HUTECH Bookstore - Hệ thống quản lý cửa hàng sách trực tuyến

## 🎯 Giới thiệu

**HUTECH Bookstore** là một ứng dụng web quản lý cửa hàng sách trực tuyến được xây dựng bằng Spring Boot. Hệ thống cung cấp đầy đủ các tính năng cho cả khách hàng và quản trị viên.

## ✨ Tính năng chính

### 👥 Khách hàng

- ✅ Đăng ký, đăng nhập tài khoản (hỗ trợ OAuth2 Google)
- 🔍 Tìm kiếm và xem danh sách sách
- 🛒 Giỏ hàng (lưu trữ cả trong session và database)
- 💳 Thanh toán và quản lý đơn hàng
- 🎫 Sử dụng mã giảm giá (voucher)
- 💬 Chat với admin
- 👤 Quản lý thông tin cá nhân

### 🔐 Quản trị viên

- 📊 Dashboard với thống kê tổng quan
- 📚 Quản lý sách (CRUD, tìm kiếm, phân loại)
- 📋 Quản lý danh mục sách
- 👥 Quản lý người dùng
- 📦 Quản lý đơn hàng (cập nhật trạng thái)
- 🎫 Quản lý voucher
- 💬 Chat với khách hàng
- 📈 Báo cáo và thống kê

## 🛠 Công nghệ sử dụng

### Backend

- **Spring Boot 4.0.2** - Framework chính
- **Spring Data JPA** - ORM và database access
- **Spring Security** - Authentication & Authorization
- **Spring OAuth2 Client** - Đăng nhập Google
- **MySQL** - Database
- **Hibernate** - ORM implementation
- **Lombok** - Giảm boilerplate code
- **Jakarta Validation** - Validation

### Frontend

- **Thymeleaf** - Template engine
- **Bootstrap 5** - CSS framework
- **jQuery** - JavaScript library
- **HTML5/CSS3**

## 📦 Cấu trúc dự án

```
TranDucMinh/
├── src/
│   ├── main/
│   │   ├── java/fit/hutech/TranDucMinh/
│   │   │   ├── constants/         # Hằng số
│   │   │   ├── controllers/       # Controllers (MVC & REST API)
│   │   │   ├── daos/              # Data Access Objects
│   │   │   ├── dto/               # Data Transfer Objects
│   │   │   ├── entities/          # JPA Entities
│   │   │   ├── exception/         # Exception handlers
│   │   │   ├── models/            # Models cho API
│   │   │   ├── repositories/      # JPA Repositories
│   │   │   ├── services/          # Business logic
│   │   │   ├── utils/             # Utilities
│   │   │   ├── validators/        # Custom validators
│   │   │   ├── viewmodels/        # View models
│   │   │   ├── AppConfig.java     # Security & Config
│   │   │   └── TranDucMinhApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/           # Stylesheets
│   │       │   └── js/            # JavaScript files
│   │       ├── templates/
│   │       │   ├── admin/         # Admin templates
│   │       │   ├── book/          # Customer templates
│   │       │   └── users/         # Auth templates
│   │       └── application.properties
│   └── test/                      # Unit tests
├── pom.xml                        # Maven dependencies
└── README.md
```

## 🚀 Cài đặt và Chạy

### Yêu cầu

- Java 21 hoặc cao hơn
- Maven 3.6+
- MySQL 8.0+

### Bước 1: Clone repository

```bash
git clone <repository-url>
cd TranDucMinh
```

### Bước 2: Cấu hình database

Tạo database MySQL:

```sql
CREATE DATABASE bookstore;
```

Cập nhật thông tin database trong `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore
spring.datasource.username=root
spring.datasource.password=your_password
```

### Bước 3: Build và chạy

```bash
# Sử dụng Maven wrapper
./mvnw spring-boot:run

# Hoặc build JAR
./mvnw clean package
java -jar target/TranDucMinh-0.0.1-SNAPSHOT.jar
```

### Bước 4: Truy cập ứng dụng

- **URL:** http://localhost:8080
- **Admin Account:**
  - Username: `admin`
  - Password: `admin123`
- **User Account:**
  - Username: `user`
  - Password: `user123`

## 📊 Database Schema

### Các bảng chính

- `user` - Thông tin người dùng
- `role` - Vai trò (ADMIN, USER)
- `user_role` - Bảng liên kết user và role
- `book` - Thông tin sách
- `category` - Danh mục sách
- `cart` - Giỏ hàng
- `cart_item` - Items trong giỏ hàng
- `invoices` - Đơn hàng
- `item_invoice` - Items trong đơn hàng
- `voucher` - Mã giảm giá
- `conversation` - Cuộc trò chuyện
- `message` - Tin nhắn

## 🔐 Bảo mật

- **Spring Security** - Authentication & Authorization
- **BCrypt** - Mã hóa mật khẩu
- **OAuth2** - Đăng nhập Google
- **CSRF Protection** - Bảo vệ khỏi CSRF attacks (tắt cho API)
- **Role-based Access Control** - Phân quyền theo vai trò
- **Session Management** - Quản lý session an toàn

## 🎨 Tối ưu đã thực hiện

### Backend

- ✅ Thêm validation cho tất cả entities và DTOs
- ✅ Global Exception Handler cho API và Web
- ✅ Logging với SLF4J
- ✅ Transaction management với SERIALIZABLE isolation
- ✅ Connection pooling với HikariCP
- ✅ Optimized JPA queries với fetch joins
- ✅ Repository methods cho search và filtering
- ✅ Audit fields (createdAt, updatedAt) cho entities
- ✅ Stock management cho books
- ✅ Voucher system với validation

### Database

- ✅ Proper indexing
- ✅ Foreign key constraints
- ✅ Optimized queries
- ✅ Connection pooling configuration

### Security

- ✅ Password encoding với BCrypt
- ✅ OAuth2 integration
- ✅ CORS configuration cho API
- ✅ Session timeout và cookie security
- ✅ Remember-me functionality

## 📝 API Documentation

### REST API Endpoints

#### Books API (`/api/books`)

- `GET /api/books` - Lấy danh sách sách (có pagination)
- `GET /api/books/{id}` - Lấy chi tiết sách
- `POST /api/books` - Tạo sách mới (Admin)
- `PUT /api/books/{id}` - Cập nhật sách (Admin)
- `DELETE /api/books/{id}` - Xóa sách (Admin)

#### Categories API (`/api/categories`)

- `GET /api/categories` - Lấy danh sách danh mục
- `GET /api/categories/{id}` - Lấy chi tiết danh mục
- `POST /api/categories` - Tạo danh mục mới (Admin)
- `PUT /api/categories/{id}` - Cập nhật danh mục (Admin)
- `DELETE /api/categories/{id}` - Xóa danh mục (Admin)

## 🧪 Testing

```bash
# Chạy tests
./mvnw test

# Chạy với coverage
./mvnw test jacoco:report
```

## 📈 Monitoring

- Health check: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/metrics

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**Trần Đức Minh**

- Email: ducminh@hutech.edu.vn
- University: HUTECH University

## 🙏 Acknowledgments

- Spring Boot Documentation
- Thymeleaf Documentation
- Bootstrap Documentation
- Stack Overflow Community

---

⭐ **Nếu bạn thấy project hữu ích, hãy cho một star nhé!** ⭐
