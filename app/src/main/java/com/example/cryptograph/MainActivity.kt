package com.example.cryptograph

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.cryptograph.auth.AuthUi
import com.example.cryptograph.auth.AuthViewModel
import com.example.cryptograph.model.StudentModel
import com.example.cryptograph.network.StudentService
import com.example.cryptograph.session.UserSessionManager
import com.example.cryptograph.ui.theme.CryptographTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userSessionManager: UserSessionManager

    @Inject
    lateinit var studentService: StudentService

    private val viewModel: AuthViewModel by viewModels()

    private var isAuthenticated by mutableStateOf<Boolean?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userSessionManager.isLoggedIn
            .onEach {
                isAuthenticated = it
            }
            .launchIn(lifecycleScope)

        setContent {
            CryptographTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isAuthenticated == false)
                        AuthUi(
                            viewModel = viewModel,
                            onAuthenticationSucceed = { isAuthenticated = true })
                    if (isAuthenticated==true) {
                        var students by remember { mutableStateOf<String?>("Loading...") }

                        LaunchedEffect(null) {
                            delay(2222)

                            studentService.postStudent(
                                StudentModel(
                                    name = "mohammad",
                                    lastName = "jahangiry"
                                )
                            )

                            val response = studentService.getAllStudents()
                            val body = response.body()
                            students = if (response.isSuccessful && body != null)
                                body.toString()
                            else
                                response.errorBody()?.string()
                        }

                        Text(text = students.toString())
                    }
                }
            }
        }
    }
}