package com.example.noto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noto.ui.theme.NotoTheme
import com.example.noto.ui.theme.blur
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import kotlin.io.path.Path

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotoTheme {
                NotoTheme(dynamicColor = false) {
                    Background()
                    NotoTitle()
                    Bottommenu()
                    }
                }
            }
        }
    }

enum class ButtonState { Pressed, Idle }
fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.70f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {  }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}
@Composable
fun Background(){
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        content = {
            // Empty
        }
    )
}



@Composable
fun NotoTitle(){
    Text(
        text = "noto.",

        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 60.sp,
            color = Color.White
        ),
        modifier = Modifier.padding(start = 40.dp, top = 100.dp)

    )

}

@Composable
fun Bottommenu(){
    val haptic = LocalHapticFeedback.current

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
                .offset(x = (-50).dp)
                .clip(RoundedCornerShape(100))
                .size(width = 200.dp, height = 60.dp)
                .background(MaterialTheme.colorScheme.secondary)

        )


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = 200.dp, height = 60.dp)
                .offset(x = (-50).dp)
        ){

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(60.dp)
                    .bounceClick()
            ) {
                Icon(
                    painter = painterResource(R.drawable.round_add_24),
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
                    painter = painterResource(R.drawable.outline_bookmarks_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                          },
                modifier = Modifier
                    .size(60.dp)
                    .bounceClick()
            ) {
                Icon(
                    painter = painterResource(R.drawable.rounded_file_export_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        M3_Hexagon(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(53.dp)
                .offset(90.dp,y = (-5).dp),
            points = 10,
            wobble = 5f
        )
        IconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(90.dp)
                .size(60.dp)
                .bounceClick()
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_settings_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }





    }
}

@Composable
fun M3_Hexagon(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    wobble: Float = 6f,      // intensity
    points: Int = 6
) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        val center = center
        val path = Path()

        val steps = 100
        for (i in 0..steps) {
            val angle = (2 * Math.PI * i / steps).toFloat()
            val wave = kotlin.math.sin(angle * points) * wobble
            val r = radius + wave

            val x = center.x + r * kotlin.math.cos(angle)
            val y = center.y + r * kotlin.math.sin(angle)

            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        drawPath(path, color)
    }
}
