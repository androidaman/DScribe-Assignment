package com.aman.unriddle_technologies_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.aman.unriddle_technologies_assignment.screens.DrawingScreen
import com.aman.unriddle_technologies_assignment.ui.theme.Unriddle_Technologies_AssignmentTheme
import com.aman.unriddle_technologies_assignment.utils.PermissionUtils.checkPermissionREAD_EXTERNAL_STORAGE

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            Unriddle_Technologies_AssignmentTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DrawingScreen(context)
                }
            }
            checkPermissionREAD_EXTERNAL_STORAGE(LocalContext.current)
        }
    }
}


