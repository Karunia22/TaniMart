package com.example.tanimart.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.ui.auth.session.state.SessionState
import com.example.tanimart.ui.auth.session.state.SessionViewModel

@Composable
fun AppNavigation(){
    val sessionViewModel: SessionViewModel = viewModel()

    val sessionState by sessionViewModel.state
        .collectAsStateWithLifecycle()

    when (sessionState) {

        SessionState.Checking -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        SessionState.Authenticated -> {
            MainNavigation(
                startDestination = "home"
            )
        }

        SessionState.Unauthenticated -> {
            MainNavigation(
                startDestination = "login"
            )
        }

    }

}