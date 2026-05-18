# AppThucPhamXanh
Ứng dụng di động hỗ trợ người dùng mua sắm thực phẩm tươi sống, rau củ quả, thịt cá và đồ ăn sẵn một cách tiện lợi và nhanh chóng. Dự án được phát triển trên nền tảng Android Studio.

---

##  Giao Diện Ứng Dụng

<p align="center">
  <img src="path_to_your_image/image_6d7d12.png" width="300" alt="Giao diện Trang Chủ"/>
</p>

---

##  Tính Năng Nổi Bật

### 1. Màn Hình Chính (Trang Chủ)
* **Phân loại danh mục (Categories):** Hiển thị danh mục trực quan bằng hình ảnh: *Rau củ, Hoa quả, Thịt, Đồ ăn*.
* **Danh sách sản phẩm bán chạy (Best Sellers):** Phân chia sản phẩm theo các mục tiêu dùng nhiều nhất dựa trên danh mục:
    * Những loại rau củ mua nhiều nhất (Màu xanh lá)
    * Những loại hoa quả mua nhiều nhất (Màu xanh dương)
    * Những loại thịt mua nhiều nhất (Màu cam)
    * Những loại đồ ăn mua nhiều nhất (Màu đỏ hồng)
* **Thanh điều hướng nhanh (Bottom Navigation Bar):** Chuyển đổi linh hoạt giữa 4 tab: *Trang chủ, Ưu đãi, Đơn hàng, Cá nhân*.

### 2. Quản Lý Giỏ Hàng & Tương Tác
* **Thêm vào giỏ hàng:** Cho phép người dùng thêm sản phẩm nhanh chóng từ danh sách.
* **Thông báo Toast/Snackbar:** Hiển thị phản hồi trực quan khi thêm thành công (*"Bạn đã thêm sản phẩm vào giỏ hàng"*).
* **Cập nhật số lượng:** Biểu tượng giỏ hàng trên Header tự động cập nhật số lượng sản phẩm real-time.

---

## Công Nghệ & Thư Viện Sử Dụng

* **IDE:** Android Studio (Java)
* **UI Components:** * `ConstraintLayout`, `LinearLayout` để thiết kế giao diện scannable, tối ưu.
    * `RecyclerView` & `CardView` để hiển thị danh sách sản phẩm mượt mà.
    * `BottomNavigationView` cho thanh menu phía dưới.
* **Database/Backend
