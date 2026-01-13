package com.example.internet_json

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var imgDetailThumbnail: ImageView
    private lateinit var tvDetailHoTen: TextView
    private lateinit var tvDetailMSSV: TextView
    private lateinit var tvDetailNgaySinh: TextView
    private lateinit var tvDetailEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Chi tiết sinh viên"

        // Initialize views
        imgDetailThumbnail = findViewById(R.id.imgDetailThumbnail)
        tvDetailHoTen = findViewById(R.id.tvDetailHoTen)
        tvDetailMSSV = findViewById(R.id.tvDetailMSSV)
        tvDetailNgaySinh = findViewById(R.id.tvDetailNgaySinh)
        tvDetailEmail = findViewById(R.id.tvDetailEmail)

        // Get data from intent
        val thumbnail = intent.getStringExtra("thumbnail") ?: ""
        val hoten = intent.getStringExtra("hoten") ?: ""
        val mssv = intent.getStringExtra("mssv") ?: ""
        val ngaysinh = intent.getStringExtra("ngaysinh") ?: ""
        val email = intent.getStringExtra("email") ?: ""

        // Display data
        tvDetailHoTen.text = hoten
        tvDetailMSSV.text = mssv
        tvDetailNgaySinh.text = ngaysinh
        tvDetailEmail.text = email

        // Load image using Glide with full URL
        val imageUrl = RetrofitClient.getImageUrl(thumbnail)

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .centerCrop()
            .into(imgDetailThumbnail)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

