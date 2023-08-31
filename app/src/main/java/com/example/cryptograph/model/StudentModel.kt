package com.example.cryptograph.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class StudentModel(
    val id: UUID? = null,
    val name: String,
    val lastName: String,
    val applicationUserID: UUID? = null
)
