package com.toletspot.houseforrent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.toletspot.houseforrent.ui.theme.newBlack
import kotlinx.coroutines.delay

// Fake models
data class User(val id: Int, val name: String)
data class Post(val id: Int, val content: String)

// Fake suspend functions
suspend fun fetchUser(): User {
    delay(2000)
    return User(1, "Alice")
}

suspend fun fetchPosts(): List<Post> {
    delay(5000)
    return listOf(
        Post(1, "Hello World"),
        Post(2, "Kotlin Coroutines 🚀")
    )
}

suspend fun fetchNotificationsCount(): Int {
    delay(10000)
    return 5
}

// UI state
data class DashboardUiState(
    val user: User? = null,
    val posts: List<Post> = emptyList(),
    val notifications: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState(isLoading = true))
    val uiState: StateFlow<DashboardUiState> = _uiState

    fun loadDashboardData() {
        viewModelScope.launch {
            try {
                // Run in parallel
                val userDeferred = async { fetchUser() }
                val postsDeferred = async { fetchPosts() }
                val notificationsDeferred = async { fetchNotificationsCount() }

                _uiState.value = DashboardUiState(
                    user = userDeferred.await(),
                    posts = postsDeferred.await(),
                    notifications = notificationsDeferred.await(),
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = DashboardUiState(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}



@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDashboardData()
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${uiState.error}",   color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(0))
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

//                LottiAnimation(6)
//                LottiAnimation(4)
//                LottiAnimation(5)
                Text(
                    text = "Hello, ${uiState.user?.name ?: "Guest"} 👋",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Notifications: ${uiState.notifications}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Posts",
                    style = MaterialTheme.typography.titleMedium
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.posts) { post ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Text(
                                text = post.content,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
