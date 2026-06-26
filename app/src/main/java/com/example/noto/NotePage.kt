package com.example.noto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
                .height(2.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        )
    }

}
@Composable
fun NoteTitle(Titletext: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)

    ) {

        Text(
            text = Titletext,

            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 60.sp,
                color = Color.White
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)

        )

    }
}
@Composable
fun NoteScroll() {


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(2220.dp)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            NoteTitle("Test")
            TitleSeperator()
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()

            )
        }
        item {
            var text by remember { mutableStateOf("enter") }
            TextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent),
                modifier = Modifier


                )
        }
        items(10) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()

            )
        }

    }
}

