package com.aman.unriddle_technologies_assignment.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.aman.unriddle_technologies_assignment.views.DrawingSurfaceView

@Composable
fun DrawingScreen(context: Context) {
    val surfaceView = remember { DrawingSurfaceView(context) }

    var isImageLoaded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { surfaceView }, modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                surfaceView.loadRandomImage()
                isImageLoaded = true
            }) {
                Text("Load Random Image")
            }

            Button(
                onClick = {
                    if (isImageLoaded) {
                        surfaceView.finalizeImage()
                        isImageLoaded = false
                    }
                },
                enabled = isImageLoaded
            ) {
                Text("Insert")
            }
        }
    }
}