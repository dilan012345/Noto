package com.example.noto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Optional: no setContentView if no layout
    }
}

@Composable
fun TitleSeperator(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(fraction = 0.9F)
                .padding(top = 200.dp)
                .height(1.dp)
                .background(color = Color.White)
        )
    }

}
