package com.ghost.ufosightingsapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ghost.ufosightingsapp.ui.screens.SightingListScreen
import com.ghost.ufosightingsapp.ui.theme.UFOSightingsAppTheme
import com.ghost.ufosightingsapp.vm.SightingViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UFOSightingsAppTheme {
                val viewModel: SightingViewModel = viewModel()
                SightingListScreen(viewModel)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SightingListScreenPreview() {
    UFOSightingsAppTheme {
        val viewModel: SightingViewModel = viewModel()
        SightingListScreen(viewModel)
    }
}