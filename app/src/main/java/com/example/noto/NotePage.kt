package com.example.noto


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.ui.focus.onFocusEvent
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.example.noto.R

class NotePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}


@Composable
fun TitleSeperator(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)


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
            .padding(top = 70.dp)
            .height(120.dp)

    ) {

        Text(
            text = Titletext,

            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 60.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)

        )

    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteScroll() {

    var text by rememberSaveable { mutableStateOf("enter") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {

        NoteTitle("Test")

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier

                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .border(
                    0.5.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.secondary)
        ) {

            TextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Default
                ),
                singleLine = false,
                maxLines = Int.MAX_VALUE,
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.coolvetica)),
                    fontSize = 20.sp
                )
            )
        }

        repeat(10) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun BottomMenuForNotes(onSettingsClick: () -> Unit, onBackClick: () -> Unit){
    val haptic = LocalHapticFeedback.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(bottom = 1.dp)

// Raised clear box to anchor menu to
    ){
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)


                .size(width = 450.dp, height = 190.dp)


                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),

                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.01f)
                        ),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(0f, 0f)
                    )
                ),
        )

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(bottom = 80.dp)
// Raised clear box to anchor menu to
    ){

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (20).dp)
                .clip(RoundedCornerShape(100))
                .size(width = 350.dp, height = 100.dp)

                //.background(MaterialTheme.colorScheme.surface)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.0f),
                            Color.Black.copy(alpha = 0.0f),
                            Color.Black.copy(alpha = 0.0f)
                        ),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(0f, 0f)
                    )
                )

        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(x = (30).dp)
                .clip(RoundedCornerShape(100))
                .size(width = 200.dp, height = 60.dp)
                .background(MaterialTheme.colorScheme.secondary)

        )



        M3_Hexagon(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(53.dp)
                .offset((-110).dp,y = (-5).dp),
            points = 10,
            wobble = 5f
        )
        IconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onBackClick()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset((-110).dp)
                .size(60.dp)
                .bounceClick()
        ) {
            Icon(
                painter = painterResource(com.example.noto.R.drawable.rounded_arrow_back_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = 200.dp, height = 60.dp)
                .offset(x = (30).dp)
        ){

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(60.dp)
                    .bounceClick()
            ) {
                Icon(
                    painter = painterResource(com.example.noto.R.drawable.round_add_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { /* bookmark action */ },
                modifier = Modifier
                    .size(60.dp)
                    .bounceClick()
            ) {
                Icon(
                    painter = painterResource(com.example.noto.R.drawable.outline_bookmarks_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            val context = LocalContext.current
            IconButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                    onSettingsClick()
                },
                modifier = Modifier
                    .size(60.dp)
                    .bounceClick()
            ) {
                Icon(
                    painter = painterResource(com.example.noto.R.drawable.outline_settings_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }





    }
}