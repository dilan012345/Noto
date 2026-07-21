package com.example.noto


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.times
import kotlinx.coroutines.launch

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


//not in use due to asynch issue
@Composable
fun NoteTitle(Titletext: String, Body: String, Id: Int, viewModel: NoteViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp)
            .height(120.dp)

    ) {

        var titletemp by remember { mutableStateOf(Titletext) }
        BasicTextField(
            value = titletemp,
            onValueChange = { titletemp = it

                viewModel.saveNote(
                    Note(
                        id = Id,
                        Title = it,
                        Body = Body,
                        Bookmarked = false
                    )



                )
                Log.d("ROOM", "Saving id=$Id title=$it")

                            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 60.sp,
                lineHeight = 20.sp

            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 40.dp),

            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )




    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteScroll(
    Title: String,
    Body: String,
    Id: Int,
    viewModel: NoteViewModel
) {
    var BodyTemp by remember { mutableStateOf(Body) }
    var TitleTemp by remember { mutableStateOf(Title) }
    val scrollState = rememberScrollState()
    var titleHeight by remember { mutableStateOf(120.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                ,


            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            // TITLE

            val titleStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 60.sp,
                lineHeight = 80.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 40.dp,
                        top = 70.dp,
                        end = 40.dp
                    )
                    .heightIn(min = 110.dp)
            ) {

                BasicTextField(
                    value = TitleTemp,
                    onValueChange = {
                        TitleTemp = it

                        viewModel.saveNote(
                            Note(
                                id = Id,
                                Title = it,
                                Body = BodyTemp,
                                Bookmarked = false
                            )
                        )

                        Log.d("ROOM", "Saving id=$Id title=$it")
                    },
                    textStyle = titleStyle,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth(),
                    onTextLayout = { layout ->
                        titleHeight = (layout.lineCount * 110).dp
                    },
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (TitleTemp.isEmpty()) {
                                Text(
                                    text = "Title",
                                    style = titleStyle.copy(
                                        color = Color.Gray
                                    )
                                )
                            }

                            innerTextField()
                        }
                    }
                )
            }





            // BODY

            var boxHeight by remember { mutableIntStateOf(0) }

            Box(
                modifier = Modifier

                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp
                    )
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        boxHeight = it.size.height
                    }
                    .border(
                        0.5.dp,
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.secondary)
            ) {

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .width(5.dp)
                        .height(50.dp)

                        .offset {
                            IntOffset(
                                x =0,
                                (
                                        scrollState.value.toFloat() /
                                                scrollState.maxValue.coerceAtLeast(1) *
                                                (boxHeight - 50)
                                        ).toInt()
                            )
                        }
                        .clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.primary)

                )



                TextField(

                    value = BodyTemp,
                    onValueChange = {
                        BodyTemp = it

                        viewModel.saveNote(
                            Note(
                                id = Id,
                                Title = TitleTemp,
                                Body = it,
                                Bookmarked = false
                            )
                        )

                        Log.d("ROOM", "Saving id=$Id body=$it")


                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            (BodyTemp.count { it == '\n' } * 30).dp
                        ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Default
                    ),
                    singleLine = false,
                    textStyle = TextStyle(
                        fontFamily = FontFamily(
                            Font(R.font.coolvetica)
                        ),
                        fontSize = 20.sp
                    )

                )
            }


            Box(modifier = Modifier
                .height(70.dp)
                .fillMaxWidth())

            Badges()
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
                painter = painterResource(R.drawable.rounded_arrow_back_24),
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
                    painter = painterResource(R.drawable.outline_settings_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }





    }
}