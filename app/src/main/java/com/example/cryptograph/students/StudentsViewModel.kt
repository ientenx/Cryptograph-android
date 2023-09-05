package com.example.cryptograph.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptograph.model.StudentModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(private val repository: StudentsRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<StudentsUiState>(StudentsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<StudentsUiEffect>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        loadAllStudents()
    }

    private fun loadAllStudents() {
        viewModelScope.launch {
            delay(2000)
            val response = repository.getAllStudents()

            val body = response.body()
            if (response.isSuccessful && body != null) {
                _uiState.update {
                    StudentsUiState.Data(body)
                }
            } else {
                _uiState.update {
                    StudentsUiState.Error
                }
            }
        }
    }

    fun saveStudent(student: StudentModel) {
        viewModelScope.launch {
            val response =
                if (student.id == null)
                    repository.postStudent(student)
                else
                    repository.updateStudent(student)

            if (response.isSuccessful) {
                _uiEffect.emit(StudentsUiEffect.StudentSaved)
                loadAllStudents()
            } else {
                response.errorBody()?.string()?.let(::println)
            }
        }
    }

    fun deleteStudent(student: StudentModel) {
        viewModelScope.launch {
            val response = repository.deleteStudent(student)

            if (response.isSuccessful) {
                _uiEffect.emit(StudentsUiEffect.StudentDeleted)
                loadAllStudents()
            } else {
                response.errorBody()?.string()?.let(::println)
            }
        }
    }
}