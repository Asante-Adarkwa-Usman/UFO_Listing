package com.ghost.ufosightingsapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghost.ufosightingsapp.R
import com.ghost.ufosightingsapp.data.model.SightingModel
import com.ghost.ufosightingsapp.data.model.SightingType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SightingItem(
    sighting: SightingModel,
    isSelected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tintGreen = Color(0xFF08A462)

    // Formatting date and time
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputDateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault())
    val outputTimeFormatter = DateTimeFormatter.ofPattern("h:mma", Locale.getDefault())

    val parsedDateTime = remember(sighting.date) {
        try {
            LocalDateTime.parse(sighting.date, inputFormatter)
        } catch (e: Exception) {
            null
        }
    }

    val displayDate = parsedDateTime?.format(outputDateFormatter) ?: "Invalid Date"
    val displayTime = parsedDateTime?.format(outputTimeFormatter) ?: "--:--"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Blob or Lampshade Icon
        val iconRes = when (sighting.type) {
            SightingType.BLOB -> R.drawable.blob_large
            SightingType.LAMPSHADE -> R.drawable.lampshade_large
        }

        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Sighting Icon",
            modifier = Modifier
                .padding(start = 10.dp, end = 16.dp)
                .size(32.dp),
            colorFilter = ColorFilter.tint(tintGreen)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = displayDate,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${sighting.speed} knots • ${sighting.type.name.replaceFirstChar { it.uppercase() }}",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = displayTime,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Chevron",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 8.dp)
        )
    }

    if (isSelected) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDelete) {
                Text(text = "Remove", color = Color.Red)
            }
        }
    }
}
