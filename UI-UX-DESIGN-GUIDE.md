# 🎨 HUTECH Bookstore - UI/UX Design System Documentation

## Modern Academic Design Framework v2.0

---

## 📑 Mục lục

1. [Giới thiệu](#giới-thiệu)
2. [Design Principles](#design-principles)
3. [Color System](#color-system)
4. [Typography](#typography)
5. [Components](#components)
6. [Layout Structure](#layout-structure)
7. [Usage Guide](#usage-guide)
8. [Best Practices](#best-practices)
9. [Migration Guide](#migration-guide)

---

## 🎯 Giới thiệu

**HUTECH Design System** là một bộ quy chuẩn thiết kế modern, clean và academic được xây dựng đặc biệt cho hệ thống HUTECH Bookstore. Design system này đảm bảo tính nhất quán, khả năng mở rộng và trải nghiệm người dùng tốt nhất.

### Đặc điểm nổi bật

- ✅ Modern Academic Style
- ✅ 100% Responsive Design
- ✅ Component-based Architecture
- ✅ Accessibility (WCAG 2.1 AA)
- ✅ Dark Mode Ready
- ✅ Performance Optimized

---

## 🎨 Design Principles

### 1. Clarity (Rõ ràng)

- Giao diện đơn giản, dễ hiểu
- Typography rõ ràng, dễ đọc
- Contrast ratio đạt chuẩn WCAG

### 2. Consistency (Nhất quán)

- Sử dụng component library thống nhất
- Color palette và spacing cố định
- Pattern design đồng bộ

### 3. Efficiency (Hiệu quả)

- Tối ưu performance
- Loading time nhanh
- Interaction mượt mà

### 4. Accessibility (Dễ tiếp cận)

- Keyboard navigation
- Screen reader support
- Color contrast đủ chuẩn

---

## 🎨 Color System

### Primary Colors

```css
--hutech-primary: #0d6efd; /* Xanh dương chủ đạo */
--hutech-primary-hover: #0b5ed7; /* Hover state */
--hutech-primary-light: #6ea8fe; /* Light variant */
--hutech-primary-lighter: #e7f1ff; /* Background */
--hutech-primary-dark: #084298; /* Dark variant */
```

**Sử dụng:**

- Primary buttons, links chính
- Active states, selected items
- Brand elements

### Semantic Colors

```css
--hutech-success: #198754; /* Thành công */
--hutech-danger: #dc3545; /* Lỗi, xóa */
--hutech-warning: #ffc107; /* Cảnh báo */
--hutech-info: #0dcaf0; /* Thông tin */
```

### Neutral Colors (Gray Scale)

```css
--hutech-gray-50: #f8f9fa; /* Background nhẹ */
--hutech-gray-100: #f1f3f5; /* Card background */
--hutech-gray-200: #e9ecef; /* Borders */
--hutech-gray-300: #dee2e6; /* Dividers */
--hutech-gray-600: #6c757d; /* Secondary text */
--hutech-gray-900: #212529; /* Primary text */
```

### Usage Examples

```html
<!-- Background Colors -->
<div class="hutech-bg-primary">Primary Background</div>
<div class="hutech-bg-light">Light Background</div>
<div class="hutech-bg-white">White Background</div>

<!-- Text Colors -->
<p class="hutech-text-primary">Primary Text</p>
<p class="hutech-text-muted">Muted Text</p>
<p class="hutech-text-success">Success Text</p>
```

---

## 📝 Typography

### Font Family

```css
--hutech-font-family:
  "Inter", "Roboto", -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
```

**Cách tích hợp:**

```html
<!-- Google Fonts -->
<link
  href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap"
  rel="stylesheet"
/>
```

### Font Sizes

| Class              | Size     | Usage                   |
| ------------------ | -------- | ----------------------- |
| `hutech-text-xs`   | 0.75rem  | Small labels, footnotes |
| `hutech-text-sm`   | 0.875rem | Secondary text          |
| `hutech-text-base` | 1rem     | Body text (default)     |
| `hutech-text-lg`   | 1.125rem | Lead paragraphs         |
| `hutech-text-xl`   | 1.25rem  | Sub-headings            |
| `hutech-text-2xl`  | 1.5rem   | Section titles          |
| `hutech-text-3xl`  | 1.875rem | Page titles             |

### Headings

```html
<h1 class="hutech-h1">Display Heading 1</h1>
<!-- 2.5rem -->
<h2 class="hutech-h2">Display Heading 2</h2>
<!-- 2rem -->
<h3 class="hutech-h3">Display Heading 3</h3>
<!-- 1.75rem -->
<h4 class="hutech-h4">Display Heading 4</h4>
<!-- 1.5rem -->
<h5 class="hutech-h5">Display Heading 5</h5>
<!-- 1.25rem -->
<h6 class="hutech-h6">Display Heading 6</h6>
<!-- 1rem -->
```

### Font Weights

```html
<p class="hutech-font-light">Light 300</p>
<p class="hutech-font-normal">Normal 400</p>
<p class="hutech-font-medium">Medium 500</p>
<p class="hutech-font-semibold">Semi-Bold 600</p>
<p class="hutech-font-bold">Bold 700</p>
```

---

## 🧩 Components

### 1. Buttons

#### Variants

```html
<!-- Primary Button -->
<button class="hutech-btn hutech-btn-primary">
  <i class="bi bi-check"></i> Primary
</button>

<!-- Outline Button -->
<button class="hutech-btn hutech-btn-outline-primary">Outline Primary</button>

<!-- Success, Danger, Warning -->
<button class="hutech-btn hutech-btn-success">Success</button>
<button class="hutech-btn hutech-btn-danger">Danger</button>
<button class="hutech-btn hutech-btn-warning">Warning</button>
```

#### Sizes

```html
<button class="hutech-btn hutech-btn-primary hutech-btn-sm">Small</button>
<button class="hutech-btn hutech-btn-primary">Default</button>
<button class="hutech-btn hutech-btn-primary hutech-btn-lg">Large</button>
```

#### States

```html
<button class="hutech-btn hutech-btn-primary" disabled>Disabled</button>
<button class="hutech-btn hutech-btn-primary hutech-btn-block">
  Full Width
</button>
<button class="hutech-btn hutech-btn-icon"><i class="bi bi-heart"></i></button>
```

### 2. Cards

```html
<!-- Basic Card -->
<div class="hutech-card">
  <div class="hutech-card-header">
    <h5>Card Title</h5>
  </div>
  <div class="hutech-card-body">
    <p>Card content goes here</p>
  </div>
  <div class="hutech-card-footer">
    <button class="hutech-btn hutech-btn-primary">Action</button>
  </div>
</div>

<!-- Elevated Card -->
<div class="hutech-card hutech-card-elevated">
  <div class="hutech-card-body">Content</div>
</div>

<!-- Bordered Card -->
<div class="hutech-card hutech-card-bordered">
  <div class="hutech-card-body">Content</div>
</div>
```

### 3. Forms

```html
<!-- Form Group -->
<div class="hutech-form-group">
  <label class="hutech-form-label hutech-form-label-required"> Username </label>
  <input type="text" class="hutech-form-control" placeholder="Enter username" />
  <small class="hutech-form-text">Helper text here</small>
</div>

<!-- Input Sizes -->
<input type="text" class="hutech-form-control hutech-form-control-sm" />
<input type="text" class="hutech-form-control" />
<input type="text" class="hutech-form-control hutech-form-control-lg" />

<!-- Input Group -->
<div class="hutech-input-group">
  <span class="hutech-input-group-text">
    <i class="bi bi-search"></i>
  </span>
  <input type="text" class="hutech-form-control" placeholder="Search..." />
  <button class="hutech-btn hutech-btn-primary">Search</button>
</div>
```

### 4. Tables

```html
<table class="hutech-table">
  <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Status</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>1</td>
      <td>John Doe</td>
      <td><span class="hutech-badge hutech-badge-success">Active</span></td>
      <td>
        <button class="hutech-btn hutech-btn-sm hutech-btn-outline-primary">
          <i class="bi bi-eye"></i>
        </button>
      </td>
    </tr>
  </tbody>
</table>

<!-- Responsive Table -->
<div class="hutech-table-responsive">
  <table class="hutech-table">
    ...
  </table>
</div>

<!-- Striped Table -->
<table class="hutech-table hutech-table-striped">
  ...
</table>
```

### 5. Badges

```html
<span class="hutech-badge hutech-badge-primary">Primary</span>
<span class="hutech-badge hutech-badge-success">Success</span>
<span class="hutech-badge hutech-badge-danger">Danger</span>
<span class="hutech-badge hutech-badge-warning">Warning</span>
<span class="hutech-badge hutech-badge-info">Info</span>
<span class="hutech-badge hutech-badge-light">Light</span>

<!-- Large Badge -->
<span class="hutech-badge hutech-badge-primary hutech-badge-lg">Large</span>
```

### 6. Alerts

```html
<div class="hutech-alert hutech-alert-success" role="alert">
  <div class="hutech-alert-icon">
    <i class="bi bi-check-circle-fill"></i>
  </div>
  <div class="hutech-alert-content">
    <div class="hutech-alert-title">Success!</div>
    <div>Your action was successful.</div>
  </div>
  <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
</div>

<!-- Variants -->
<div class="hutech-alert hutech-alert-danger">Error Alert</div>
<div class="hutech-alert hutech-alert-warning">Warning Alert</div>
<div class="hutech-alert hutech-alert-info">Info Alert</div>
```

### 7. Pagination

```html
<ul class="hutech-pagination">
  <li>
    <a class="hutech-pagination-link disabled" href="#">
      <i class="bi bi-chevron-left"></i>
    </a>
  </li>
  <li>
    <a class="hutech-pagination-link active" href="#">1</a>
  </li>
  <li>
    <a class="hutech-pagination-link" href="#">2</a>
  </li>
  <li>
    <a class="hutech-pagination-link" href="#">3</a>
  </li>
  <li>
    <a class="hutech-pagination-link" href="#">
      <i class="bi bi-chevron-right"></i>
    </a>
  </li>
</ul>
```

---

## 🏗️ Layout Structure

### User Layout (Front-end)

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
  <head th:replace="~{book/layout-modern::head(~{::title})}">
    <title>Page Title</title>
  </head>
  <body>
    <!-- Header -->
    <header th:replace="~{book/layout-modern::header}"></header>

    <!-- Main Content -->
    <main class="container my-5">
      <!-- Your content here -->
    </main>

    <!-- Footer -->
    <footer th:replace="~{book/layout-modern::footer}"></footer>

    <!-- Scripts -->
    <div th:replace="~{book/layout-modern::scripts}"></div>
  </body>
</html>
```

### Admin Layout

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
  <head th:replace="~{admin/layout-modern::admin-head(~{::title})}">
    <title>Admin - Page Title</title>
  </head>
  <body>
    <div class="d-flex">
      <!-- Sidebar -->
      <nav th:replace="~{admin/layout-modern::sidebar('activePage')}"></nav>

      <!-- Main Content -->
      <div class="hutech-main-content flex-grow-1">
        <!-- Top Bar -->
        <div
          th:replace="~{admin/layout-modern::topbar('Page Title', 'Subtitle')}"
        ></div>

        <!-- Content -->
        <div class="hutech-content-wrapper">
          <!-- Your admin content here -->
        </div>
      </div>
    </div>

    <!-- Scripts -->
    <div th:replace="~{admin/layout-modern::admin-scripts}"></div>
  </body>
</html>
```

---

## 📖 Usage Guide

### Sử dụng Thymeleaf Fragments

#### 1. Book Card Component

```html
<!-- In your Thymeleaf template -->
<div class="row g-4">
  <div class="col-lg-3 col-md-4 col-sm-6" th:each="book : ${books}">
    <div th:replace="~{components/ui-components::book-card(${book})}"></div>
  </div>
</div>
```

#### 2. Form Input Component

```html
<div
  th:replace="~{components/ui-components::form-input(
    id='username',
    label='Username',
    type='text',
    name='username',
    value=${user.username},
    placeholder='Enter username',
    required=true,
    error=${usernameError}
)}"
></div>
```

#### 3. Status Badge

```html
<!-- Order Status -->
<span
  th:replace="~{components/ui-components::order-status-badge(${order.status})}"
></span>

<!-- Stock Status -->
<span
  th:replace="~{components/ui-components::stock-badge(${book.stockQuantity})}"
></span>
```

#### 4. Pagination

```html
<div
  th:replace="~{components/ui-components::pagination-enhanced(${page})}"
></div>
```

#### 5. Empty State

```html
<div
  th:replace="~{components/ui-components::empty-state(
    icon='bi-inbox',
    title='No Data',
    message='No items found',
    actionUrl='/add',
    actionText='Add New'
)}"
></div>
```

### Responsive Grid System

```html
<!-- Bootstrap 5 Grid -->
<div class="container">
  <div class="row g-4">
    <div class="col-lg-3 col-md-4 col-sm-6 col-12">
      <!-- Content -->
    </div>
  </div>
</div>
```

**Breakpoints:**

- `sm`: ≥576px (Mobile landscape)
- `md`: ≥768px (Tablet)
- `lg`: ≥992px (Desktop)
- `xl`: ≥1200px (Large desktop)
- `xxl`: ≥1400px (Extra large)

---

## ✅ Best Practices

### 1. Component Usage

#### ✅ DO

```html
<!-- Use semantic HTML -->
<article class="hutech-card">
  <header class="hutech-card-header">
    <h3>Title</h3>
  </header>
  <div class="hutech-card-body">
    <p>Content</p>
  </div>
</article>

<!-- Use Thymeleaf fragments for reusable components -->
<div th:replace="~{components/ui-components::book-card(${book})}"></div>
```

#### ❌ DON'T

```html
<!-- Don't inline styles -->
<div style="background: blue; padding: 20px;">Content</div>

<!-- Don't duplicate component code -->
<div class="custom-card">...</div>
<div class="custom-card">...</div>
```

### 2. Accessibility

```html
<!-- Always use proper ARIA labels -->
<button class="hutech-btn hutech-btn-icon" aria-label="Close">
  <i class="bi bi-x" aria-hidden="true"></i>
</button>

<!-- Use semantic HTML -->
<nav aria-label="Breadcrumb">
  <ol class="breadcrumb">
    ...
  </ol>
</nav>

<!-- Provide alt text for images -->
<img src="book.jpg" alt="Book title by Author name" />
```

### 3. Performance

```html
<!-- Load critical CSS in head -->
<head>
  <link rel="stylesheet" th:href="@{/css/hutech-design-system.css}" />
</head>

<!-- Defer non-critical scripts -->
<script defer th:src="@{/js/custom.js}"></script>

<!-- Use loading attribute for images -->
<img src="image.jpg" loading="lazy" alt="Description" />
```

### 4. Naming Conventions

```css
/* Use BEM-like naming */
.hutech-card {
}
.hutech-card-header {
}
.hutech-card-body {
}
.hutech-card--elevated {
}

/* Use semantic names */
.hutech-btn-primary {
} /* ✅ Good */
.blue-button {
} /* ❌ Bad */
```

---

## 🔄 Migration Guide

### Từ Old Design sang New Design System

#### Step 1: Update CSS References

```html
<!-- OLD -->
<link rel="stylesheet" th:href="@{/css/style.css}" />

<!-- NEW -->
<link rel="stylesheet" th:href="@{/css/hutech-design-system.css}" />
```

#### Step 2: Update Layout

```html
<!-- OLD -->
<th:block th:replace="~{layout::header}"></th:block>

<!-- NEW -->
<header th:replace="~{book/layout-modern::header}"></header>
```

#### Step 3: Update Components

```html
<!-- OLD Button -->
<button class="btn btn-primary">Click Me</button>

<!-- NEW Button -->
<button class="hutech-btn hutech-btn-primary">
  <i class="bi bi-check"></i> Click Me
</button>
```

#### Step 4: Update Cards

```html
<!-- OLD Card -->
<div class="card">
  <div class="card-body">Content</div>
</div>

<!-- NEW Card -->
<div class="hutech-card">
  <div class="hutech-card-body">Content</div>
</div>
```

### Class Mapping Table

| Old Class       | New Class              | Notes           |
| --------------- | ---------------------- | --------------- |
| `.btn`          | `.hutech-btn`          | Button base     |
| `.btn-primary`  | `.hutech-btn-primary`  | Primary button  |
| `.card`         | `.hutech-card`         | Card component  |
| `.form-control` | `.hutech-form-control` | Form input      |
| `.badge`        | `.hutech-badge`        | Badge component |
| `.alert`        | `.hutech-alert`        | Alert component |
| `.table`        | `.hutech-table`        | Table component |

---

## 🎓 Examples & Templates

### Complete Page Example

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
  <head th:replace="~{book/layout-modern::head(~{::title})}">
    <title>Products - HUTECH Bookstore</title>
  </head>
  <body>
    <!-- Header -->
    <header th:replace="~{book/layout-modern::header}"></header>

    <!-- Main Content -->
    <main class="container my-5">
      <!-- Flash Messages -->
      <div th:replace="~{components/ui-components::flash-messages}"></div>

      <!-- Page Header -->
      <div class="row mb-4">
        <div class="col-md-6">
          <h2 class="fw-bold">Products</h2>
        </div>
        <div class="col-md-6 text-end">
          <a href="/add" class="hutech-btn hutech-btn-primary">
            <i class="bi bi-plus-circle"></i> Add New
          </a>
        </div>
      </div>

      <!-- Content Grid -->
      <div class="row g-4">
        <div class="col-lg-3 col-md-4 col-sm-6" th:each="book : ${books}">
          <div
            th:replace="~{components/ui-components::book-card(${book})}"
          ></div>
        </div>
      </div>

      <!-- Pagination -->
      <div class="mt-5">
        <div
          th:replace="~{components/ui-components::pagination-enhanced(${page})}"
        ></div>
      </div>
    </main>

    <!-- Footer -->
    <footer th:replace="~{book/layout-modern::footer}"></footer>

    <!-- Scripts -->
    <div th:replace="~{book/layout-modern::scripts}"></div>
  </body>
</html>
```

---

## 📞 Support & Resources

### Documentation Links

- Bootstrap 5: https://getbootstrap.com/docs/5.3/
- Bootstrap Icons: https://icons.getbootstrap.com/
- Thymeleaf: https://www.thymeleaf.org/doc/

### Internal Resources

- Design System CSS: `/css/hutech-design-system.css`
- User Layout: `/templates/book/layout-modern.html`
- Admin Layout: `/templates/admin/layout-modern.html`
- Components: `/templates/components/ui-components.html`

### Quick Links

- [README.md](README.md) - Project overview
- [API.md](API.md) - API documentation
- [DEVELOPMENT.md](DEVELOPMENT.md) - Development guide

---

## 📝 Changelog

### Version 2.0.0 (Current)

- ✨ New Modern Academic Design System
- 🎨 Complete UI/UX redesign
- 🧩 Component library với Thymeleaf fragments
- 📱 100% responsive design
- ♿ Improved accessibility
- 🚀 Performance optimization

### Version 1.0.0 (Legacy)

- Basic Bootstrap styling
- Simple layout structure

---

## 👨‍💻 Author

**Trần Đức Minh**

- Email: ducminh@hutech.edu.vn
- University: HUTECH University

---

**Copyright © 2024-2026 HUTECH Bookstore. All rights reserved.**
