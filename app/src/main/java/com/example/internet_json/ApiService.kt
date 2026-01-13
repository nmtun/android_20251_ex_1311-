package com.example.internet_json

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("students")
    fun getStudents(): Call<List<Student>>
}

