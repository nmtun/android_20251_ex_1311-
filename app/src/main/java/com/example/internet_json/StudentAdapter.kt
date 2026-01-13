package com.example.internet_json

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StudentAdapter(
    private var students: List<Student>,
    private val onItemClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var filteredStudents: List<Student> = students

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumbnail: ImageView = itemView.findViewById(R.id.imgThumbnail)
        val tvHoTen: TextView = itemView.findViewById(R.id.tvHoTen)
        val tvMSSV: TextView = itemView.findViewById(R.id.tvMSSV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = filteredStudents[position]

        holder.tvHoTen.text = student.hoten
        holder.tvMSSV.text = student.mssv

        // Load image using Glide with full URL
        val imageUrl = RetrofitClient.getImageUrl(student.thumbnail)

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .override(200, 200) // Resize image to avoid loading large images
            .centerCrop()
            .into(holder.imgThumbnail)

        holder.itemView.setOnClickListener {
            onItemClick(student)
        }
    }

    override fun getItemCount(): Int = filteredStudents.size

    fun filter(query: String) {
        filteredStudents = if (query.isEmpty()) {
            students
        } else {
            students.filter {
                it.hoten.contains(query, ignoreCase = true) ||
                it.mssv.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    fun updateData(newStudents: List<Student>) {
        students = newStudents
        filteredStudents = newStudents
        notifyDataSetChanged()
    }
}

