package com.ghost.ufosightingsapp.vm

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghost.ufosightingsapp.data.model.SightingModel
import com.ghost.ufosightingsapp.data.model.SightingType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SightingViewModel : ViewModel(){
    private val _sightings = MutableStateFlow<List<SightingModel>>(emptyList())
    val sightings = _sightings.asStateFlow()
    private val _showSnackbar = MutableSharedFlow<String>()
    val showSnackbar = _showSnackbar.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addSighting() {
        val randomSpeed = (2..50).random()
        val type = if ((0..1).random() == 0) SightingType.BLOB else SightingType.LAMPSHADE
        val date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)

        val newSighting = SightingModel(
            date = date,
            type = type,
            speed = randomSpeed
        )
        _sightings.value += newSighting
    }

    fun removeSighting(sighting: SightingModel) {
        _sightings.value -= sighting
        viewModelScope.launch {
            _showSnackbar.emit("Sighting removed")
        }
    }
}