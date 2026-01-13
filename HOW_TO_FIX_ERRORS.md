# ⚠️ HƯỚNG DẪN SỬA LỖI - ĐANG BÁO ĐỎ

## Nguyên nhân:
Các dependencies (Retrofit, Glide, CardView) chưa được tải về và đồng bộ trong dự án.

## ✅ CÁCH SỬA (Thực hiện theo thứ tự):

### Bước 1: Sync Gradle Dependencies
**Cách 1 (Khuyến nghị):**
1. Mở Android Studio
2. Tìm thanh thông báo màu vàng/xanh ở trên cùng
3. Click vào nút **"Sync Now"**
4. Đợi Gradle sync xong (có thể mất 1-3 phút)

**Cách 2:**
1. Vào menu: **File** → **Sync Project with Gradle Files**
2. Đợi quá trình sync hoàn tất

**Cách 3:**
1. Click vào biểu tượng **"Elephant"** (Gradle) ở thanh công cụ
2. Click **"Sync Project with Gradle Files"**

### Bước 2: Rebuild Project
Sau khi sync xong:
1. Vào menu: **Build** → **Clean Project**
2. Đợi Clean xong
3. Vào menu: **Build** → **Rebuild Project**
4. Đợi Rebuild xong

### Bước 3: Invalidate Caches (Nếu vẫn lỗi)
1. Vào menu: **File** → **Invalidate Caches**
2. Chọn **"Invalidate and Restart"**
3. Android Studio sẽ khởi động lại

---

## 🔍 Kiểm tra Dependencies đã được thêm:

Mở file `app/build.gradle.kts` và kiểm tra có các dòng sau:

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")  // ← Phải có dòng này
}

dependencies {
    // RecyclerView & CardView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    // Retrofit - REST API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Glide - Image Loading
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")  // ← Phải là kapt, không phải annotationProcessor
}
```

---

## 🌐 Kiểm tra Internet Connection:
- Gradle cần Internet để tải dependencies lần đầu
- Nếu bị firewall, hãy tắt tạm thời hoặc cấu hình proxy

---

## 📱 Sau khi sửa xong:

Tất cả các lỗi đỏ sẽ biến mất:
- ✅ `Unresolved reference 'retrofit2'` → Biến mất
- ✅ `Unresolved reference 'bumptech'` (Glide) → Biến mất
- ✅ `Unresolved reference 'Callback'` → Biến mất
- ✅ Các lỗi R.id.* trong DetailActivity → Biến mất

---

## 🚨 Nếu vẫn còn lỗi:

### Lỗi: "Could not resolve all artifacts..."
**Giải pháp:**
1. Kiểm tra kết nối Internet
2. Thử đổi Maven repository trong `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }  // Thêm dòng này nếu cần
    }
}
```

### Lỗi: "R.id.* cannot be found"
**Giải pháp:**
1. Build → Clean Project
2. Build → Rebuild Project
3. R class sẽ được tạo lại

---

## ✅ Kiểm tra cuối cùng:

Sau khi sync thành công, bạn sẽ thấy:
- ✅ Không còn gạch đỏ dưới import statements
- ✅ Không còn lỗi "Unresolved reference"
- ✅ Build successful
- ✅ App có thể chạy được

---

## 📞 Lưu ý quan trọng:

**KHÔNG CHỈNH SỬA CODE KHI ĐANG SYNC!**
- Đợi Gradle sync xong hẵn chỉnh sửa
- Nếu sync bị lỗi, đọc thông báo lỗi trong Build Output

**Thời gian sync lần đầu:**
- Có thể mất 3-5 phút (tùy tốc độ Internet)
- Các lần sau sẽ nhanh hơn

---

## 🎯 TÓM TẮT NHANH:

1. ⚙️ **Sync Gradle** (Click "Sync Now")
2. 🔨 **Build → Rebuild Project**
3. ✅ **Kiểm tra lỗi biến mất**
4. 🚀 **Run App**

Chúc bạn thành công! 🎉

