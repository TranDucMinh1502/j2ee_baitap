# 🚀 HUTECH Design System - Quick Start Guide

## Bắt đầu nhanh với Design System mới

---

## 📦 Files đã được tạo

### 1. CSS Framework

```
src/main/resources/static/css/
└── hutech-design-system.css    # Design system chính (1200+ dòng)
```

### 2. Layouts

```
src/main/resources/templates/
├── book/
│   └── layout-modern.html      # Layout cho User/Customer
└── admin/
    └── layout-modern.html      # Layout cho Admin Panel
```

### 3. Components Library

```
src/main/resources/templates/components/
└── ui-components.html          # Reusable Thymeleaf fragments
```

### 4. Example Pages

```
src/main/resources/templates/
├── book/
│   ├── index-modern.html      # Homepage mới
│   └── list-modern.html       # Product listing mới
└── admin/
    └── dashboard-modern.html  # Admin dashboard mới
```

---

## ⚡ Cách sử dụng

### 1. Tạo trang User mới

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
  <head th:replace="~{book/layout-modern::head(~{::title})}">
    <title>Trang của bạn - HUTECH Bookstore</title>
  </head>
  <body>
    <!-- Header/Navbar -->
    <header th:replace="~{book/layout-modern::header}"></header>

    <!-- Nội dung chính -->
    <main class="container my-5">
      <h1>Xin chào!</h1>
      <p>Đây là trang của bạn</p>

      <!-- Sử dụng components -->
      <div class="row g-4">
        <div class="col-md-4" th:each="book : ${books}">
          <div
            th:replace="~{components/ui-components::book-card(${book})}"
          ></div>
        </div>
      </div>
    </main>

    <!-- Footer -->
    <footer th:replace="~{book/layout-modern::footer}"></footer>

    <!-- Scripts -->
    <div th:replace="~{book/layout-modern::scripts}"></div>
  </body>
</html>
```

### 2. Tạo trang Admin mới

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
  <head th:replace="~{admin/layout-modern::admin-head(~{::title})}">
    <title>Admin - Dashboard</title>
  </head>
  <body>
    <div class="d-flex">
      <!-- Sidebar -->
      <nav th:replace="~{admin/layout-modern::sidebar('dashboard')}"></nav>

      <!-- Main Content -->
      <div class="hutech-main-content flex-grow-1">
        <!-- Top Bar -->
        <div
          th:replace="~{admin/layout-modern::topbar('Dashboard', 'Tổng quan')}"
        ></div>

        <!-- Content -->
        <div class="hutech-content-wrapper">
          <h1>Admin Content</h1>

          <!-- Stat Cards -->
          <div class="row g-4">
            <div class="col-md-3">
              <div class="hutech-stat-card">
                <div class="hutech-stat-label">Tổng sách</div>
                <div class="hutech-stat-value">150</div>
                <div class="hutech-stat-change positive">+12% tháng này</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Scripts -->
    <div th:replace="~{admin/layout-modern::admin-scripts}"></div>
  </body>
</html>
```

---

## 🎨 Components phổ biến

### Buttons

```html
<!-- Primary -->
<button class="hutech-btn hutech-btn-primary">
  <i class="bi bi-check"></i> Lưu
</button>

<!-- Sizes -->
<button class="hutech-btn hutech-btn-primary hutech-btn-sm">Nhỏ</button>
<button class="hutech-btn hutech-btn-primary">Vừa</button>
<button class="hutech-btn hutech-btn-primary hutech-btn-lg">Lớn</button>

<!-- With icon only -->
<button class="hutech-btn hutech-btn-icon hutech-btn-primary">
  <i class="bi bi-heart"></i>
</button>
```

### Cards

```html
<!-- Basic Card -->
<div class="hutech-card">
  <div class="hutech-card-header">
    <h5>Tiêu đề Card</h5>
  </div>
  <div class="hutech-card-body">
    <p>Nội dung card</p>
  </div>
</div>

<!-- Book Card (using component) -->
<div th:replace="~{components/ui-components::book-card(${book})}"></div>
```

### Forms

```html
<!-- Form Input -->
<div class="hutech-form-group">
  <label class="hutech-form-label hutech-form-label-required"> Tên sách </label>
  <input type="text" class="hutech-form-control" placeholder="Nhập tên sách" />
  <small class="hutech-form-text">Tên sách không quá 255 ký tự</small>
</div>

<!-- Or use component -->
<div
  th:replace="~{components/ui-components::form-input(
    id='title',
    label='Tên sách',
    type='text',
    name='title',
    value=${book.title},
    placeholder='Nhập tên sách',
    required=true,
    error=${titleError}
)}"
></div>
```

### Badges

```html
<span class="hutech-badge hutech-badge-success">Còn hàng</span>
<span class="hutech-badge hutech-badge-danger">Hết hàng</span>
<span class="hutech-badge hutech-badge-warning">Sắp hết</span>
```

### Alerts

```html
<div class="hutech-alert hutech-alert-success">
  <div class="hutech-alert-icon">
    <i class="bi bi-check-circle-fill"></i>
  </div>
  <div class="hutech-alert-content">
    <div class="hutech-alert-title">Thành công!</div>
    <div>Đã lưu thay đổi</div>
  </div>
</div>
```

### Tables

```html
<table class="hutech-table">
  <thead>
    <tr>
      <th>ID</th>
      <th>Tên</th>
      <th>Trạng thái</th>
    </tr>
  </thead>
  <tbody>
    <tr th:each="item : ${items}">
      <td th:text="${item.id}">1</td>
      <td th:text="${item.name}">Name</td>
      <td>
        <span class="hutech-badge hutech-badge-success">Active</span>
      </td>
    </tr>
  </tbody>
</table>
```

---

## 🎯 Common Patterns

### 1. Page Header với Search & Action

```html
<div class="row mb-4">
  <div class="col-md-8">
    <h2 class="fw-bold">Danh sách sách</h2>
    <p class="hutech-text-muted">Quản lý tất cả sách trong hệ thống</p>
  </div>
  <div class="col-md-4 text-end">
    <a href="/books/add" class="hutech-btn hutech-btn-primary">
      <i class="bi bi-plus-circle"></i> Thêm sách
    </a>
  </div>
</div>
```

### 2. Grid với Cards

```html
<div class="row g-4">
  <div class="col-lg-3 col-md-4 col-sm-6" th:each="book : ${books}">
    <div th:replace="~{components/ui-components::book-card(${book})}"></div>
  </div>
</div>
```

### 3. Empty State

```html
<div
  th:replace="~{components/ui-components::empty-state(
    icon='bi-inbox',
    title='Chưa có sách nào',
    message='Hãy thêm sách đầu tiên của bạn',
    actionUrl='/books/add',
    actionText='Thêm sách mới'
)}"
></div>
```

### 4. Pagination

```html
<!-- Ở cuối danh sách -->
<div class="mt-5">
  <div
    th:replace="~{components/ui-components::pagination-enhanced(${page})}"
  ></div>
</div>
```

---

## 🎨 Color Classes

```html
<!-- Text Colors -->
<p class="hutech-text-primary">Primary text</p>
<p class="hutech-text-success">Success text</p>
<p class="hutech-text-danger">Danger text</p>
<p class="hutech-text-muted">Muted text</p>

<!-- Background Colors -->
<div class="hutech-bg-primary text-white p-3">Primary background</div>
<div class="hutech-bg-light p-3">Light background</div>
```

---

## 📐 Spacing Utilities

```html
<!-- Margin -->
<div class="hutech-mt-3">Margin top</div>
<div class="hutech-mb-4">Margin bottom</div>

<!-- Padding -->
<div class="hutech-p-3">Padding all sides</div>
<div class="hutech-p-5">Padding large</div>

<!-- Gap (for flexbox/grid) -->
<div class="hutech-d-flex hutech-gap-md">
  <button>Button 1</button>
  <button>Button 2</button>
</div>
```

---

## 🔧 Migration từ trang cũ

### Bước 1: Thay CSS

```html
<!-- CŨ -->
<link rel="stylesheet" th:href="@{/css/style.css}" />

<!-- MỚI -->
<link rel="stylesheet" th:href="@{/css/hutech-design-system.css}" />
```

### Bước 2: Thay Layout

```html
<!-- CŨ -->
<th:block th:replace="~{layout::header}"></th:block>

<!-- MỚI -->
<header th:replace="~{book/layout-modern::header}"></header>
```

### Bước 3: Cập nhật Classes

```html
<!-- CŨ -->
<button class="btn btn-primary">Click</button>

<!-- MỚI -->
<button class="hutech-btn hutech-btn-primary">
  <i class="bi bi-check"></i> Click
</button>
```

---

## 📱 Responsive Breakpoints

```
Mobile:      < 576px   (xs)
Tablet:      ≥ 768px   (md)
Desktop:     ≥ 992px   (lg)
Large:       ≥ 1200px  (xl)
Extra Large: ≥ 1400px  (xxl)
```

**Example:**

```html
<div class="col-12 col-md-6 col-lg-4">
  <!-- 12 cols on mobile, 6 on tablet, 4 on desktop -->
</div>
```

---

## 📚 Tài liệu đầy đủ

Xem file [UI-UX-DESIGN-GUIDE.md](UI-UX-DESIGN-GUIDE.md) để có hướng dẫn chi tiết!

---

## ✅ Checklist áp dụng Design System

- [ ] Import `hutech-design-system.css`
- [ ] Sử dụng `layout-modern.html` cho layout
- [ ] Thay thế classes cũ bằng `hutech-*` classes
- [ ] Sử dụng components từ `ui-components.html`
- [ ] Test responsive trên mobile/tablet
- [ ] Kiểm tra accessibility
- [ ] Tối ưu performance

---

## 🤝 Cần hỗ trợ?

1. Xem documentation: `UI-UX-DESIGN-GUIDE.md`
2. Xem example pages: `*-modern.html`
3. Check component library: `components/ui-components.html`
4. Liên hệ: ducminh@hutech.edu.vn

**Happy Coding! 🎉**
