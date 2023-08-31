package com.example.cryptograph.students

import com.example.cryptograph.model.StudentModel
import com.example.cryptograph.network.StudentService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentsRepository @Inject constructor(private val service: StudentService) {

    suspend fun getAllStudents(): Response<List<StudentModel>> =
        service.getAllStudents()

    suspend fun postStudent(student: StudentModel): Response<StudentModel> =
        service.postStudent(student)

    suspend fun updateStudent(student: StudentModel): Response<StudentModel> =
        service.putStudent(student.id.toString(), student)

    suspend fun deleteStudent(student: StudentModel): Response<Any> =
        service.deleteStudent(studentId = student.id.toString())
}