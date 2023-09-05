package com.example.cryptograph.students

import com.example.cryptograph.model.StudentModel

sealed interface StudentsUiState {
    data object Loading: StudentsUiState
    data class Data(val students: List<StudentModel>) : StudentsUiState
    data object Error: StudentsUiState
}

sealed interface StudentsUiEffect {
    data object StudentSaved : StudentsUiEffect
    data object StudentDeleted : StudentsUiEffect
}