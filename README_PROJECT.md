# Ứng dụng Quản lý Sinh viên - Internet JSON

## Tổng quan
Ứng dụng Android hiển thị danh sách sinh viên từ API và cho phép xem chi tiết, tìm kiếm.

## Các chức năng đã hoàn thành:

### ✅ 1. Kết nối và lấy dữ liệu từ API
- API: https://lebavui.io.vn/students
- Sử dụng Retrofit và Gson để kết nối và parse JSON
- Hiển thị ProgressBar khi đang tải dữ liệu

### ✅ 2. Hiển thị danh sách sinh viên
- RecyclerView với CardView cho mỗi item
- Mỗi item hiển thị: thumbnail (ảnh), họ tên, MSSV
- Sử dụng Glide để load ảnh từ URL

### ✅ 3. Xem chi tiết sinh viên
- Click vào sinh viên để mở DetailActivity
- Hiển thị đầy đủ: thumbnail, họ tên, MSSV, ngày sinh, email
- Giao diện đẹp với CardView và layout chi tiết

### ✅ 4. Tìm kiếm sinh viên
- SearchView ở đầu màn hình
- Tìm kiếm theo họ tên hoặc MSSV
- Kết quả cập nhật real-time khi gõ

## Cấu trúc dự án:

### Models:
- `Student.kt` - Data class chứa thông tin sinh viên

### API:
- `ApiService.kt` - Interface định nghĩa API endpoints
- `RetrofitClient.kt` - Singleton khởi tạo Retrofit

### UI:
- `MainActivity.kt` - Màn hình chính hiển thị danh sách
- `DetailActivity.kt` - Màn hình chi tiết sinh viên
- `StudentAdapter.kt` - Adapter cho RecyclerView

### Layouts:
- `activity_main.xml` - Layout chính với SearchView và RecyclerView
- `item_student.xml` - Layout cho mỗi item trong danh sách
- `activity_detail.xml` - Layout cho màn hình chi tiết

## Dependencies đã thêm:
- Retrofit 2.9.0 - REST API client
- Gson Converter 2.9.0 - JSON parsing
- Glide 4.16.0 - Image loading
- RecyclerView 1.3.2 - Danh sách cuộn
- CardView 1.0.0 - UI components

## Permissions:
- INTERNET - Kết nối mạng
- ACCESS_NETWORK_STATE - Kiểm tra trạng thái mạng

## Hướng dẫn sử dụng:
1. Sync Gradle để tải dependencies
2. Build và chạy ứng dụng
3. Danh sách sinh viên sẽ tự động load từ API
4. Nhập tên hoặc MSSV vào ô tìm kiếm để lọc
5. Click vào sinh viên để xem chi tiết

## Lưu ý:
- Cần kết nối Internet để ứng dụng hoạt động
- Ảnh thumbnail được load từ URL trong dữ liệu API
- Nếu không load được ảnh, sẽ hiển thị icon mặc định

