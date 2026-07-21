package com.example.noto


import android.R.attr.progress
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Debug
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.rememberPlatformOverscrollFactory
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noto.ui.theme.NotoTheme
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import kotlin.math.cos
import kotlin.math.sin
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import androidx.compose.ui.draw.rotate

class Homepage : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notes_database"
        ).build()
    }

    private val viewModel by lazy {
        NoteViewModel(db.noteDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        window.setNavigationBarContrastEnforced(false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightNavigationBars = true}


            setContent {

                NotoTheme {


                    NotoTheme(dynamicColor = false) {

                        AppNavigation(
                            viewModel = viewModel
                        )

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
fun Title(Titletext: String){
    Text(
        text = Titletext,

        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 60.sp,
            color = Color.White
        ),
        modifier = Modifier.padding(start = 40.dp, top = 100.dp)

    )

}

@Composable
fun Bottommenu(viewModel: NoteViewModel,onSettingsClick: () -> Unit, onImportClick: () -> Unit, onAddClick: () -> Unit){
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
                .offset(x = (-40).dp)
                .clip(RoundedCornerShape(100))
                .size(width = 200.dp, height = 60.dp)
                .background(MaterialTheme.colorScheme.secondary)

        )


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = 200.dp, height = 60.dp)
                .offset(x = (-40).dp)
        ){

            IconButton(
                onClick = {
                    println("ADD CLICKED")

                    onAddClick()

                          },
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

        M3_Hexagon(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(53.dp)
                .offset(100.dp,y = (-5).dp),
            points = 10,
            wobble = 5f
        )
        IconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onImportClick()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(100.dp)
                .size(60.dp)
                .bounceClick()
        ) {
            Icon(
                painter = painterResource(R.drawable.rounded_file_export_24),
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
            val wave = sin(angle * points) * wobble
            val r = radius + wave

            val x = center.x + r * cos(angle)
            val y = center.y + r * sin(angle)

            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        drawPath(path, color)
    }
}















@Composable
fun DelConfirm(){
    var showBox by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(100.dp)
    )
}

fun Modifier.clickableWithRipple(
    rippleColor: Color = Color.Red,
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(color = rippleColor),
        onClick = onClick
    )
}
fun Modifier.combinedClickableWithRipple(
    rippleColor: Color = Color.Red,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null
): Modifier = composed {
    val haptics = LocalHapticFeedback.current
    this.combinedClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(color = rippleColor),
        onClick = onClick,
        onLongClick = onLongClick?.let { userLongClick ->
            {
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                userLongClick()
            }
        },
        onDoubleClick = onDoubleClick
    )
}

@Composable
fun NoteGrid(viewModel: NoteViewModel, onNoteClick: (Note) -> Unit) {
    val noteList by viewModel.notes.collectAsState(initial = emptyList())
    var showDel by remember { mutableStateOf(false) }
    val hazeState = remember { HazeState() }
    var selectedNote by remember { mutableStateOf<Note?>(null) }
    val pullToRefreshState = rememberPullToRefreshState()
    val pullDistance = pullToRefreshState.distanceFraction.coerceIn(0f, 1f)
    val infiniteTransition = rememberInfiniteTransition(label = "snailSpin")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = {},
        state = pullToRefreshState,
        indicator = {
            val progress = pullToRefreshState.distanceFraction.coerceIn(0f, 1f)
            val scale by animateFloatAsState(
                targetValue = if (pullToRefreshState.distanceFraction > 0f) {
                    pullToRefreshState.distanceFraction.coerceIn(0f, 1f)
                } else {
                    0f
                },
                label = "Scale"
            )


            Icon(
                painter = painterResource(R.drawable.refresh),
                tint = null,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(80.dp)
                    .scale(scale)
                    .padding(top = 30.dp)
                    .graphicsLayer {
                        scaleX = progress
                        scaleY = progress
                        rotationZ = if (progress > 0f) rotation else 0f
                    }

            )
        }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 0.dp,
                end = 12.dp,
                bottom = 50.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.hazeSource(hazeState)
                .overscroll(rememberOverscrollEffect())
                .graphicsLayer {
                    translationY = pullDistance * 80f
                    scaleY = 1f + (pullDistance * 0.05f)},


        ) {

            item(span = { GridItemSpan(maxLineSpan) }) {
                Title("noto.")
            }

            if (noteList.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .border(
                                0.5.dp,
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.snail),
                            contentDescription = null,
                            tint = null,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .size(80.dp)
                        )

                        Text(
                            text = "(No notes to display, click + to create some!)",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }

            items(count = noteList.size) { index ->
                val Note = noteList[index]

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .border(
                            0.5.dp,
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.secondary)

                        .combinedClickableWithRipple(
                            rippleColor = MaterialTheme.colorScheme.primary,
                            onClick = {
                                onNoteClick(Note)
                            },
                            onLongClick = {
                                showDel = true
                                selectedNote = Note
                            }
                        )
                        .padding(12.dp)
                ) {

                    Column {

                        Text(
                            text = Note.Title,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 7.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .width(700.dp)
                                .height(1.dp)
                                .background(color = Color.White)
                        )

                        Text(
                            text = Note.Body,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 15.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {

                Badges()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ){
                Icon(
                    painter = painterResource(R.drawable.dev),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .padding(bottom = 50.dp)
                        .size(50.dp)
                )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (showDel) {
            Box(
            modifier = Modifier
                .fillMaxSize()
        )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        showDel = false
                    }
                    .hazeEffect(
                        state = hazeState,
                        style = HazeStyle(
                            backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                            blurRadius = 55.dp,
                            tint = HazeTint(
                                Color.Black.copy(alpha = 0.4f)
                            )
                        )
                    )
                    .background(
                        Color.White.copy(alpha = 0.2f)
                    )
            )

            AnimatedVisibility(
                visible = showDel,
                enter = slideInVertically(
                    initialOffsetY = { it / 2 }
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { it / 2 }
                ) + fadeOut()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Box(
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .fillMaxWidth(0.94f)
                            .height(250.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                // Null to overwrite background clickable
                            }
                            .border(
                                0.5.dp,
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.background)

                    ) {

                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopCenter)
                                .fillMaxWidth(0.84f)
                                .height(70.dp)
                        ) {

                            Text(
                                text = "delete?",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 35.sp,
                                color = Color.White,
                                modifier = Modifier.padding(top = 20.dp)
                            )
                        }


                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                                .fillMaxWidth(0.84f)
                                .height(70.dp)
                                .border(
                                    0.5.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    color = MaterialTheme.colorScheme.secondaryContainer
                                )
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.secondary)

                        ) {

                            Text(
                                text = "This action is permanent and cannot be undone.",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                color = Color.White,
                                modifier = Modifier.padding(
                                    top = 15.dp,
                                    start = 20.dp
                                )
                            )
                        }


                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 20.dp)
                                .size(width = 2000.dp, height = 60.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Button(
                                onClick = { showDel = false },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceBright
                                ),
                                modifier = Modifier
                                    .padding(top = 10.dp, end = 10.dp)
                            ) {

                                Text(
                                    text = "Back",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            }


                            Button(
                                onClick = {
                                    selectedNote?.let {
                                        viewModel.deleteNote(it)
                                    }
                                    showDel = false
                                },
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 10.dp)
                            ) {

                                Text(
                                    text = "Delete",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}





@SuppressLint("SuspiciousIndentation")
@Composable
fun Badges(){
    val uriHandler = LocalUriHandler.current
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)

        ){
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(width = 200.dp, height = 60.dp)
                ,
                horizontalArrangement = Arrangement.Center,
            ){
                IconButton(
                    onClick = { uriHandler.openUri("https://github.com/dilan012345/Noto")},
                    modifier = Modifier
                        .size(60.dp)
                        .bounceClick()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.github_logo_green),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )

                }
                IconButton(onClick = { uriHandler.openUri("https://developer.android.com/compose")},
                    modifier = Modifier
                        .size(60.dp)
                        .bounceClick()) {
                    Icon(
                        painter = painterResource(R.drawable.jetpack_compose_green),
                        contentDescription = null,
                        tint = Color.Unspecified,

                        )
                }
                IconButton(onClick = { uriHandler.openUri("https://dilan012345.github.io/Noto/")},
                    modifier = Modifier
                        .size(60.dp)
                        .bounceClick()) {
                    Icon(
                        painter = painterResource(R.drawable.noto_logo),
                        contentDescription = null,
                        tint = Color.Unspecified,

                        )
                }


            }
        }
    }
