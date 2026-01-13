package com.example.internet_json

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        progressBar = findViewById(R.id.progressBar)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter(emptyList()) { student ->
            // Handle item click
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("thumbnail", student.thumbnail)
                putExtra("hoten", student.hoten)
                putExtra("mssv", student.mssv)
                putExtra("ngaysinh", student.ngaysinh)
                putExtra("email", student.email)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Setup SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })

        // Fetch data from API
        fetchStudents()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fetchStudents() {
        progressBar.visibility = View.VISIBLE
        Log.d("MainActivity", "Fetching students from API...")

        RetrofitClient.instance.getStudents().enqueue(object : Callback<List<Student>> {
            override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val students = response.body() ?: emptyList()
                    Log.d("MainActivity", "Received ${students.size} students")
                    adapter.updateData(students)

                    if (students.isEmpty()) {
                        Toast.makeText(this@MainActivity, "Không có dữ liệu", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("MainActivity", "API Error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@MainActivity, "Lỗi: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("MainActivity", "Connection Error: ${t.message}", t)
                Toast.makeText(this@MainActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}