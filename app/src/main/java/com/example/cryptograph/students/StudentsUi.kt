package com.example.cryptograph.students

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cryptograph.model.StudentModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsUi(viewModel: StudentsViewModel = viewModel()) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val showToast: (String) -> Unit = remember(context) {
        { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    val uiState by viewModel.uiState.collectAsState()
    val uiEffect by viewModel.uiEffect.collectAsState(initial = null)

    var studentToEdit by remember { mutableStateOf<StudentModel?>(null) }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            is StudentsUiEffect.StudentSaved -> showToast("Student Added")
            is StudentsUiEffect.StudentDeleted -> showToast("Student Deleted")
            else -> {}
        }
    }

//    BackHandler(bottomSheetScaffoldState.bottomSheetState.isVisible) {
//        scope.launch {
//            bottomSheetScaffoldState.bottomSheetState.hide()
//        }
//    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is StudentsUiState.Loading -> CircularProgressIndicator()
                is StudentsUiState.Data -> StudentsList(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = bottomSheetScaffoldState,
                    students = (uiState as StudentsUiState.Data).students,
                    studentToEdit = studentToEdit,
                    onStudentClick = { student ->
                        studentToEdit = student
                        scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                    },
                    onSaveStudent = { student ->
                        viewModel.saveStudent(student)
                        // scope.launch { bottomSheetScaffoldState.bottomSheetState.hide() }
                        studentToEdit = null
                    },
                    onDeleteStudent = viewModel::deleteStudent
                )

                is StudentsUiState.Error -> Text(text = "Error")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudentsList(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    students: List<StudentModel>,
    studentToEdit: StudentModel?,
    onStudentClick: (StudentModel) -> Unit,
    onSaveStudent: (StudentModel) -> Unit,
    onDeleteStudent: (StudentModel) -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val (name, setName) = remember(studentToEdit) {
                    mutableStateOf(studentToEdit?.name ?: "")
                }
                val (lastname, setLastname) = remember(studentToEdit) {
                    mutableStateOf(studentToEdit?.lastName ?: "")
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "First Name")
                    },
                    value = name,
                    onValueChange = setName
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Last Name")
                    },
                    value = lastname,
                    onValueChange = setLastname
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    val student = StudentModel(
                        id = studentToEdit?.id,
                        name = name,
                        lastName = lastname,
                        applicationUserID = studentToEdit?.applicationUserID
                    )
                    onSaveStudent(student)
                }) {
                    Text(text = "Save Student")
                }
            }
        }) {
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(students) { student ->
                Student(modifier = Modifier
                    .clickable {
                        onStudentClick(student)
                    }
                    .padding(horizontal = 8.dp), student = student, onDeleteClick = onDeleteStudent)
            }
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
private fun Student(
    modifier: Modifier = Modifier,
    student: StudentModel,
    onDeleteClick: (StudentModel) -> Unit
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .clickable { onDeleteClick(student) }
                        .padding(4.dp),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null
                )
            }
            Text(text = student.lastName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}