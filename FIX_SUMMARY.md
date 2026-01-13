# Hướng dẫn sửa lỗi "System UI isn't responding"

## Vấn đề
Ứng dụng bị treo với thông báo "System UI isn't responding" khi chạy. Nguyên nhân chính:

1. **URL ảnh không đầy đủ**: API trả về thumbnail dạng đường dẫn tương đối (ví dụ: `/images/abc.jpg`) thay vì URL đầy đủ
2. **Timeout không được cấu hình**: Kết nối mạng không có thời gian timeout
3. **Tải ảnh kích thước lớn**: Glide tải ảnh gốc kích thước lớn gây lag

## Các thay đổi đã thực hiện

### 1. RetrofitClient.kt - Thêm helper function và timeout
✅ Thêm OkHttpClient với timeout 30 giây
✅ Tạo function `getImageUrl()` để ghép BASE_URL với thumbnail path
✅ Xử lý cả URL đầy đủ và đường dẫn tương đối

```kotlin
fun getImageUrl(thumbnail: String?): String {
    if (thumbnail.isNullOrEmpty()) return ""
    
    return if (thumbnail.startsWith("http://") || thumbnail.startsWith("https://")) {
        thumbnail
    } else {
        val path = if (thumbnail.startsWith("/")) thumbnail.substring(1) else thumbnail
        BASE_URL + path
    }
}
```

### 2. StudentAdapter.kt - Tối ưu hóa load ảnh
✅ Sử dụng `RetrofitClient.getImageUrl()` để tạo URL đầy đủ
✅ Thêm `.override(200, 200)` để resize ảnh nhỏ hơn
✅ Thêm `.centerCrop()` để crop ảnh phù hợp

### 3. DetailActivity.kt - Tối ưu hóa load ảnh
✅ Sử dụng `RetrofitClient.getImageUrl()` để tạo URL đầy đủ
✅ Thêm `.centerCrop()` để hiển thị ảnh đẹp hơn

### 4. AndroidManifest.xml - Cấu hình network
✅ Thêm `android:usesCleartextTraffic="true"` cho phép HTTP
✅ Thêm `android:hardwareAccelerated="true"` tăng hiệu suất render

### 5. MyGlideModule.kt - Tối ưu Glide (mới)
✅ Cấu hình cache và decode format
✅ Thêm timeout 15 giây cho việc load ảnh
✅ Sử dụng RGB_565 format để tiết kiệm bộ nhớ

### 6. MainActivity.kt - Thêm logging
✅ Thêm Log.d để debug
✅ Thêm xử lý lỗi chi tiết hơn

### 7. build.gradle.kts - Thêm dependencies
✅ Thêm OkHttp và OkHttp logging interceptor
✅ Đảm bảo đủ dependencies cho Glide annotation processor

## Cách test

1. **Sync Gradle**: Click "Sync Now" hoặc File > Sync Project with Gradle Files
2. **Clean & Rebuild**: Build > Clean Project, sau đó Build > Rebuild Project
3. **Chạy ứng dụng**: Run > Run 'app'
4. **Kiểm tra Logcat**: Xem logs với tag "MainActivity" để debug

## Kiểm tra logs

Trong Logcat, tìm các dòng:
- `Fetching students from API...` - Đang gọi API
- `Received X students` - Nhận được dữ liệu thành công
- `API Error: XXX` - Lỗi API
- `Connection Error: XXX` - Lỗi kết nối

## Các tình huống xử lý

### Nếu thumbnail là đường dẫn tương đối
- `/images/student1.jpg` → `https://lebavui.io.vn/images/student1.jpg`
- `images/student1.jpg` → `https://lebavui.io.vn/images/student1.jpg`

### Nếu thumbnail là URL đầy đủ
- `https://example.com/image.jpg` → giữ nguyên

### Nếu ảnh không load được
- Hiển thị placeholder: `R.mipmap.ic_launcher`
- Không crash app

## Lưu ý quan trọng

1. **Đảm bảo có kết nối Internet** trước khi test
2. **Kiểm tra API endpoint**: https://lebavui.io.vn/students phải hoạt động
3. **Timeout đã được tăng lên 30 giây** cho API và 15 giây cho ảnh
4. **Ảnh được resize xuống 200x200** trong RecyclerView để tăng hiệu suất

## Nếu vẫn còn lỗi

1. Kiểm tra Logcat để xem lỗi cụ thể
2. Kiểm tra định dạng JSON từ API có đúng không
3. Thử truy cập https://lebavui.io.vn/students trên trình duyệt
4. Kiểm tra URL ảnh có trả về đúng không

## Kết luận

Tất cả các thay đổi đã được thực hiện để:
- ✅ Sửa lỗi URL ảnh không đầy đủ
- ✅ Thêm timeout để tránh ANR
- ✅ Tối ưu hóa việc load ảnh
- ✅ Cải thiện xử lý lỗi và logging

Bây giờ bạn có thể **Sync Gradle** và chạy lại ứng dụng!

