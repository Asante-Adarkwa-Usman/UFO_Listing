package com.ghost.ufosightingsapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghost.ufosightingsapp.data.model.SightingModel
import com.ghost.ufosightingsapp.ui.components.SightingItem
import com.ghost.ufosightingsapp.vm.SightingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SightingListScreen(viewModel: SightingViewModel) {
    val sightings by viewModel.sightings.collectAsState()
    var selectedSighting by remember { mutableStateOf<SightingModel?>(null) }

    //snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val tintGreen = Color(0xFF08A462)

  //  Listen to ViewModel's snackbar flow
    LaunchedEffect(Unit) {
        viewModel.showSnackbar.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "UFO Sightings",
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    },
                    actions = {
                        IconButton(onClick = { viewModel.addSighting() }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Sighting",
                                tint = tintGreen
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
                HorizontalDivider(thickness = 2.dp, color = Color.LightGray.copy(alpha = 0.4f))
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(sightings) { sighting ->
                SightingItem(
                    sighting = sighting,
                    isSelected = sighting == selectedSighting,
                    onClick = { selectedSighting = sighting },
                    onDelete = {
                        viewModel.removeSighting(sighting)
                        selectedSighting = null
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 48.dp),
                    thickness = 0.8.dp,
                    color = Color.LightGray.copy(alpha = 0.3f)

                )
            }
        }
    }
}
