package com.example.cryptograph.network

import co.naji.tosse.cryptography.crypto.spring.type.DecryptionType
import co.naji.tosse.cryptography.crypto.spring.type.EncryptionType
import com.example.cryptograph.model.StudentModel
import com.example.network.DecryptResponse
import com.example.network.EncryptRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentService {

    @GET("api/student")
    @DecryptResponse(method = DecryptionType.ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM)
    suspend fun getAllStudents(): Response<List<StudentModel>>

    @POST("api/student")
    @EncryptRequest(method = EncryptionType.ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM)
    @DecryptResponse(method = DecryptionType.ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM)
    suspend fun postStudent(@Body studentModel: StudentModel): Response<StudentModel>

    @PUT("api/student/{id}")
    @EncryptRequest(method = EncryptionType.ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM)
    @DecryptResponse(method = DecryptionType.ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM)
    suspend fun putStudent(
        @Path("id") studentId: String, @Body studentModel: StudentModel
    ): Response<StudentModel>

    @DELETE("api/student/{id}")
    suspend fun deleteStudent(
        @Path("id") studentId: String
    ): Response<Any>
}