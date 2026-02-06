# 🎨 HUTECH Design System - Implementation Notes

## Ghi chú triển khai cho Developers

---

## ⚠️ Quan trọng - Đọc trước khi bắt đầu

### Files mới vs Files cũ

Design system này tạo các files **MỚI**, **KHÔNG** ghi đè files cũ:

#### ✅ Files MỚI (sử dụng design system 2.0)

```
CSS:
- hutech-design-system.css    ← Design system mới

Layouts:
- book/layout-modern.html      ← User layout mới
- admin/layout-modern.html     ← Admin layout mới

Pages:
- book/index-modern.html       ← Homepage mới
- book/list-modern.html        ← Product list mới
- admin/dashboard-modern.html  ← Dashboard mới

Components:
- components/ui-components.html ← Component library
```

#### 📦 Files CŨ (vẫn giữ nguyên)

```
CSS:
- style.css                    ← CSS cũ (không động chạm)

Layouts:
- book/layout.html             ← Layout cũ
- admin/layout.html            ← Admin layout cũ

Pages:
- book/index.html              ← Homepage cũ
- book/list.html               ← List cũ
- admin/dashboard.html         ← Dashboard cũ
```

### Cách chuyển đổi

#### Option 1: Tạo trang mới (Recommended)

```java
// Controller - Tạo endpoint mới
@GetMapping("/modern")
public String modernIndex(Model model) {
    // Logic giống index cũ
    return "book/index-modern";  // ← Trỏ đến template mới
}
```

#### Option 2: Thay thế trang cũ

```java
// Controller - Đổi return template
@GetMapping("/")
public String index(Model model) {
    return "book/index-modern";  // Thay vì "book/index"
}
```

---

## 🔧 Cấu hình Controller

### User Controllers

```java
@Controller
@RequestMapping("/books")
public class BookController {

    // Homepage mới
    @GetMapping
    public String index(Model model) {
        List<Book> books = bookService.findFeaturedBooks();
        List<Category> categories = categoryService.findAll();

        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        model.addAttribute("totalBooks", bookService.count());
        model.addAttribute("totalUsers", userService.count());

        return "book/index-modern";  // ← Template mới
    }

    // Product list mới
    @GetMapping("/list")
    public String list(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size,
        @RequestParam(required = false) String sort,
        Model model
    ) {
        Page<Book> books = bookService.searchBooks(
            keyword, categoryId, minPrice, maxPrice,
            PageRequest.of(page, size, getSortOrder(sort))
        );

        model.addAttribute("books", books);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);

        return "book/list-modern";  // ← Template mới
    }

    private Sort getSortOrder(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by("id").descending();
        }
        String[] parts = sort.split(",");
        String field = parts[0];
        Sort.Direction direction = parts.length > 1 &&
            parts[1].equalsIgnoreCase("desc") ?
            Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, field);
    }
}
```

### Admin Controllers

```java
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Stats
        model.addAttribute("totalBooks", bookService.count());
        model.addAttribute("totalUsers", userService.count());
        model.addAttribute("totalOrders", orderService.count());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());

        // Recent orders
        List<Order> recentOrders = orderService.findRecentOrders(10);
        model.addAttribute("recentOrders", recentOrders);

        return "admin/dashboard-modern";  // ← Template mới
    }

    @GetMapping("/products")
    public String products(
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        Model model
    ) {
        Page<Book> books = bookService.search(
            keyword,
            PageRequest.of(page, 20)
        );

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);

        return "admin/products-modern";  // Cần tạo
    }
}
```

---

## 🎨 Tích hợp với Backend

### 1. Cart Item Count (Header)

```java
@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CartService cartService;

    @ModelAttribute
    public void addCartItemCount(
        Model model,
        Authentication authentication
    ) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            int itemCount = cartService.getItemCount(username);
            model.addAttribute("cartItemCount", itemCount);
        }
    }
}
```

### 2. Flash Messages

```java
@PostMapping("/books/add")
public String addBook(
    @ModelAttribute Book book,
    RedirectAttributes redirectAttributes
) {
    try {
        bookService.save(book);
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "Đã thêm sách thành công!"
        );
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute(
            "errorMessage",
            "Lỗi: " + e.getMessage()
        );
    }
    return "redirect:/admin/products";
}
```

### 3. Pagination với Page Object

```java
// Service
public Page<Book> searchBooks(
    String keyword,
    Long categoryId,
    Double minPrice,
    Double maxPrice,
    Pageable pageable
) {
    if (keyword != null || categoryId != null ||
        minPrice != null || maxPrice != null) {
        return bookRepository.searchWithFilters(
            keyword, categoryId, minPrice, maxPrice, pageable
        );
    }
    return bookRepository.findAll(pageable);
}

// Controller
@GetMapping("/list")
public String list(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "12") int size,
    Model model
) {
    Page<Book> books = bookService.searchBooks(
        null, null, null, null,
        PageRequest.of(page, size)
    );
    model.addAttribute("books", books);
    return "book/list-modern";
}
```

---

## 🗂️ Cấu trúc thư mục đề xuất

```
src/main/resources/templates/
├── book/                          # User pages
│   ├── layout-modern.html         # Layout mới
│   ├── index-modern.html          # Homepage
│   ├── list-modern.html           # Product list
│   ├── detail-modern.html         # Product detail (tạo mới)
│   ├── cart-modern.html           # Cart page (tạo mới)
│   ├── checkout-modern.html       # Checkout (tạo mới)
│   ├── my-orders-modern.html      # Orders (tạo mới)
│   ├── profile-modern.html        # Profile (tạo mới)
│   └── chat-modern.html           # Chat (tạo mới)
│
├── admin/                         # Admin pages
│   ├── layout-modern.html         # Layout mới
│   ├── dashboard-modern.html      # Dashboard
│   ├── products-modern.html       # Products management (tạo mới)
│   ├── product-form-modern.html   # Add/Edit product (tạo mới)
│   ├── categories-modern.html     # Categories (tạo mới)
│   ├── orders-modern.html         # Orders (tạo mới)
│   ├── users-modern.html          # Users (tạo mới)
│   ├── vouchers-modern.html       # Vouchers (tạo mới)
│   └── reports-modern.html        # Reports (tạo mới)
│
├── users/                         # Auth pages
│   ├── login-modern.html          # Login (tạo mới)
│   ├── register-modern.html       # Register (tạo mới)
│   └── access-denied-modern.html  # 403 (tạo mới)
│
└── components/                    # Reusable components
    └── ui-components.html         # Component library
```

---

## 📝 Template mẫu cho các trang còn lại

### Product Detail Page

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
  <head th:replace="~{book/layout-modern::head(~{::title})}">
    <title th:text="${book.title} + ' - HUTECH Bookstore'">Book Detail</title>
  </head>
  <body>
    <header th:replace="~{book/layout-modern::header}"></header>

    <main class="container my-5">
      <div class="row">
        <!-- Book Image -->
        <div class="col-lg-4">
          <img
            th:src="${book.imageUrl}"
            th:alt="${book.title}"
            class="img-fluid hutech-rounded-lg hutech-shadow"
          />
        </div>

        <!-- Book Info -->
        <div class="col-lg-8">
          <h1 class="fw-bold mb-3" th:text="${book.title}">Book Title</h1>
          <p class="hutech-text-muted mb-3">
            <i class="bi bi-person"></i>
            <span th:text="${book.author}">Author</span>
          </p>

          <div
            class="hutech-book-price mb-4"
            th:text="${#numbers.formatDecimal(book.price, 0, 'COMMA', 0, 'POINT')} + ' ₫'"
          >
            100,000 ₫
          </div>

          <p th:text="${book.description}">Description</p>

          <form th:action="@{/cart/add}" method="post" class="mt-4">
            <input type="hidden" name="bookId" th:value="${book.id}" />
            <div class="d-flex gap-3">
              <input
                type="number"
                name="quantity"
                value="1"
                min="1"
                class="hutech-form-control"
                style="max-width: 100px;"
              />
              <button
                type="submit"
                class="hutech-btn hutech-btn-primary hutech-btn-lg"
              >
                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
              </button>
            </div>
          </form>
        </div>
      </div>
    </main>

    <footer th:replace="~{book/layout-modern::footer}"></footer>
    <div th:replace="~{book/layout-modern::scripts}"></div>
  </body>
</html>
```

### Admin Product Management

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
  <head th:replace="~{admin/layout-modern::admin-head(~{::title})}">
    <title>Quản lý sách - Admin</title>
  </head>
  <body>
    <div class="d-flex">
      <nav th:replace="~{admin/layout-modern::sidebar('products')}"></nav>

      <div class="hutech-main-content flex-grow-1">
        <div
          th:replace="~{admin/layout-modern::topbar('Quản lý sách', 'Danh sách tất cả sách')}"
        ></div>

        <div class="hutech-content-wrapper">
          <div th:replace="~{admin/layout-modern::alerts}"></div>

          <!-- Table Header -->
          <div
            th:replace="~{components/ui-components::table-header(
                    title='Danh sách sách',
                    searchPlaceholder='Tìm tên sách, tác giả...',
                    addUrl='/admin/products/add',
                    addText='Thêm sách mới'
                )}"
          ></div>

          <!-- Table -->
          <div class="hutech-card">
            <div class="table-responsive">
              <table class="hutech-table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Tên sách</th>
                    <th>Tác giả</th>
                    <th>Danh mục</th>
                    <th>Giá</th>
                    <th>Tồn kho</th>
                    <th>Thao tác</th>
                  </tr>
                </thead>
                <tbody>
                  <tr th:each="book : ${books.content}">
                    <td th:text="${book.id}">1</td>
                    <td th:text="${book.title}">Title</td>
                    <td th:text="${book.author}">Author</td>
                    <td th:text="${book.category.name}">Category</td>
                    <td
                      th:text="${#numbers.formatDecimal(book.price, 0, 'COMMA', 0, 'POINT')} + '₫'"
                    >
                      100,000₫
                    </td>
                    <td>
                      <span
                        th:replace="~{components/ui-components::stock-badge(${book.stockQuantity})}"
                      ></span>
                    </td>
                    <td>
                      <div class="d-flex gap-2">
                        <a
                          th:href="@{/admin/products/edit/{id}(id=${book.id})}"
                          class="hutech-btn hutech-btn-sm hutech-btn-outline-primary"
                        >
                          <i class="bi bi-pencil"></i>
                        </a>
                        <button
                          type="button"
                          class="hutech-btn hutech-btn-sm hutech-btn-danger"
                          onclick="confirmDelete([[${book.id}]], [[${book.title}]])"
                        >
                          <i class="bi bi-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Pagination -->
          <div class="mt-4">
            <div
              th:replace="~{components/ui-components::pagination-enhanced(${books})}"
            ></div>
          </div>
        </div>
      </div>
    </div>

    <div th:replace="~{admin/layout-modern::admin-scripts}"></div>

    <script>
      function confirmDelete(id, title) {
        if (confirm(`Bạn có chắc muốn xóa sách "${title}"?`)) {
          fetch(`/admin/products/${id}`, {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json",
            },
          }).then((response) => {
            if (response.ok) {
              location.reload();
            }
          });
        }
      }
    </script>
  </body>
</html>
```

---

## 🔍 Testing & Debugging

### Kiểm tra responsive

```javascript
// Console JavaScript để test breakpoints
const checkBreakpoint = () => {
  const width = window.innerWidth;
  if (width < 576) return "xs";
  if (width < 768) return "sm";
  if (width < 992) return "md";
  if (width < 1200) return "lg";
  if (width < 1400) return "xl";
  return "xxl";
};

console.log("Current breakpoint:", checkBreakpoint());
window.addEventListener("resize", () => {
  console.log("Breakpoint:", checkBreakpoint());
});
```

### Debug CSS

```html
<!-- Thêm vào <head> để debug -->
<style>
  * {
    outline: 1px solid rgba(255, 0, 0, 0.2);
  }
  * * {
    outline: 1px solid rgba(0, 255, 0, 0.2);
  }
  * * * {
    outline: 1px solid rgba(0, 0, 255, 0.2);
  }
</style>
```

---

## 📊 Performance Tips

### 1. Lazy Loading Images

```html
<img
  th:src="${book.imageUrl}"
  th:alt="${book.title}"
  loading="lazy"
  class="hutech-book-image"
/>
```

### 2. Minimize CSS

```bash
# Minify CSS in production
npm install -g cssnano-cli
cssnano hutech-design-system.css hutech-design-system.min.css
```

### 3. Cache Static Resources

```java
// application.properties
spring.web.resources.cache.cachecontrol.max-age=365d
spring.web.resources.chain.strategy.content.enabled=true
```

---

## ✅ Completion Checklist

### Phase 1: Core Setup ✅

- [x] Design System CSS
- [x] User Layout
- [x] Admin Layout
- [x] Component Library
- [x] Example Pages
- [x] Documentation

### Phase 2: Remaining Pages (TODO)

- [ ] Product detail page
- [ ] Cart page
- [ ] Checkout page
- [ ] My orders page
- [ ] Profile page
- [ ] Login/Register pages
- [ ] Admin product management
- [ ] Admin categories
- [ ] Admin orders
- [ ] Admin users

### Phase 3: Polish (TODO)

- [ ] Animation refinements
- [ ] Loading states
- [ ] Error pages (404, 500)
- [ ] Email templates
- [ ] Print styles testing

---

## 🎯 Next Steps

1. **Test các trang mẫu**
   - Truy cập `/books/modern` (nếu đã tạo endpoint)
   - Kiểm tra responsive
   - Test interactions

2. **Tạo các trang còn lại**
   - Sử dụng templates mẫu ở trên
   - Copy-paste và customize

3. **Update Controllers**
   - Trỏ endpoints sang templates mới
   - Hoặc tạo endpoints mới

4. **Testing đầy đủ**
   - Functional testing
   - Responsive testing
   - Cross-browser testing

---

## 📞 Support

Issues hoặc questions:

- Check documentation: `UI-UX-DESIGN-GUIDE.md`
- Quick reference: `QUICK-START-UI.md`
- Email: ducminh@hutech.edu.vn

**Happy Coding! 🚀**
