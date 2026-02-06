# 📝 Changelog

Tất cả các thay đổi quan trọng của dự án sẽ được ghi lại trong file này.

## [Unreleased]

## [1.0.0] - 2026-02-06

### ✨ Features

- ✅ Hệ thống quản lý cửa hàng sách trực tuyến hoàn chỉnh
- ✅ Authentication & Authorization với Spring Security
- ✅ OAuth2 login với Google
- ✅ Quản lý sách (CRUD operations)
- ✅ Quản lý danh mục sách
- ✅ Giỏ hàng với session và database persistence
- ✅ Hệ thống đơn hàng (order management)
- ✅ Hệ thống voucher/mã giảm giá
- ✅ Chat giữa khách hàng và admin
- ✅ Dashboard admin với thống kê
- ✅ Quản lý người dùng
- ✅ REST API cho mobile/external integration
- ✅ Responsive design với Bootstrap 5

### 🔧 Optimizations

- ✅ Thêm `spring-boot-starter-validation` dependency
- ✅ Thêm `spring-boot-starter-oauth2-client` dependency
- ✅ Đổi tên file `Appconfig.java` thành `AppConfig.java`
- ✅ Thêm `@Builder.Default` cho các field có giá trị khởi tạo trong entities
- ✅ Thêm audit fields (`createdAt`, `updatedAt`) cho User entity
- ✅ Thêm `description`, `stock`, `imageUrl` fields cho Book entity
- ✅ Cải thiện `application.properties` với comprehensive configuration
- ✅ HikariCP connection pooling configuration
- ✅ Hibernate performance optimizations (batch operations, query optimization)
- ✅ Thêm logging configuration với proper levels
- ✅ Session management configuration
- ✅ CORS configuration cho API

### 🎯 Services Layer

- ✅ Thêm logging với SLF4J cho tất cả services
- ✅ Thêm JavaDoc cho các methods quan trọng
- ✅ Cải thiện BookService với search, filter, stock management
- ✅ Cải thiện CategoryService với validation
- ✅ Cải thiện ReportService để sử dụng audit fields
- ✅ Transaction management với SERIALIZABLE isolation
- ✅ Proper exception handling

### 📊 Repositories

- ✅ Thêm search methods cho IBookRepository
- ✅ Thêm filter methods (by category, price range)
- ✅ Thêm pagination support
- ✅ Thêm query đếm users mới theo khoảng thời gian
- ✅ Thêm query tìm users theo role
- ✅ Optimized queries với fetch joins

### 🎨 Controllers

- ✅ Global Exception Handler cho API và Web
- ✅ Proper error messages và flash attributes
- ✅ Validation với `@Valid`
- ✅ RESTful API endpoints cho Books và Categories
- ✅ Pagination support cho API

### 🐛 Bug Fixes

- ✅ Sửa deprecated `Locale` constructor
- ✅ Xóa unused imports
- ✅ Sửa unused variables warning
- ✅ Sửa Lombok `@Builder` warnings
- ✅ Compilation errors resolved

### 📚 Documentation

- ✅ Comprehensive README.md
- ✅ API documentation (API.md)
- ✅ Development guide (DEVELOPMENT.md)
- ✅ Changelog (CHANGELOG.md)
- ✅ Code comments và JavaDoc
- ✅ .gitignore configuration

### 🔒 Security

- ✅ BCrypt password encoding
- ✅ OAuth2 Google integration
- ✅ Role-based access control
- ✅ CSRF protection (configurable for API)
- ✅ Remember-me functionality
- ✅ Session security configuration

### 🗄️ Database

- ✅ MySQL integration
- ✅ JPA entities với proper relationships
- ✅ Database initialization với sample data
- ✅ Audit fields cho tracking
- ✅ Optimized queries và indexes

### 📦 Build & Deployment

- ✅ Maven wrapper included
- ✅ Spring Boot executable JAR
- ✅ Clean compilation with no errors
- ✅ Ready for deployment

## Technical Improvements

### Code Quality

- Removed all unused imports
- Fixed all compilation warnings
- Consistent code formatting
- Proper exception handling
- Comprehensive logging

### Performance

- Connection pooling với HikariCP (max 10 connections)
- Hibernate batch operations (batch size: 20)
- Lazy loading cho relationships
- Query optimization với fetch joins
- Proper transaction management

### Maintainability

- Clean code structure
- Separation of concerns
- DRY principle
- SOLID principles
- Comprehensive documentation

## Breaking Changes

Không có breaking changes trong version này (first release).

## Migration Guide

Không cần migration (first release).

## Known Issues

- User entity ban đầu chưa có `createdAt`, `updatedAt` - đã được thêm vào
- ReportService tạm thời hiển thị mock data cho new users stats - đã sửa để sử dụng query thật

## Future Enhancements

- [ ] Thêm email service cho order confirmation
- [ ] Thêm payment gateway integration
- [ ] Thêm product reviews và ratings
- [ ] Thêm wishlist feature
- [ ] Thêm advanced search với filters
- [ ] Thêm inventory management
- [ ] Thêm shipping tracking
- [ ] Thêm analytics dashboard
- [ ] Thêm mobile app API
- [ ] Thêm caching với Redis
- [ ] Thêm full-text search với Elasticsearch
- [ ] Thêm automated testing (unit + integration)
- [ ] Thêm CI/CD pipeline
- [ ] Thêm Docker containerization
- [ ] Thêm Kubernetes deployment configs

## Contributors

- **Trần Đức Minh** - Initial work and optimization

---

**Note:** Dự án đã được rà soát, tối ưu và hoàn thiện toàn diện theo yêu cầu.
