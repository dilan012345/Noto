package com.example.noto
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.noto.ui.theme.NotoTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext

class Settings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NotoTheme {
                NotoTheme(dynamicColor = false) {
                    BackButton(
                        onBackClick = { finish() }  // just go back to previous activity
                    )
                }
            }
        }
        }
    }
data class SettingItem(
    val title: String,
    var isEnabled: Boolean

)
@Composable
fun Haze(){
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

    }}


@Composable
fun BackButton(onBackClick: () -> Unit){
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        M3_Hexagon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(53.dp)
                .offset(10.dp, y = (55).dp),
            points = 10,
            wobble = 5f
        )
        IconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onBackClick()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(5.dp, y = (52).dp)
                .size(60.dp)
                .bounceClick()
        ) {
            Icon(
                painter = painterResource(R.drawable.rounded_arrow_back_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
@Composable
fun AppVersionText(): String? {
    val context = LocalContext.current
    val versionName = try {
        context.packageManager
            .getPackageInfo(context.packageName, 0)
            .versionName
    } catch (e: Exception) {
        "Unknown"
    }
    return versionName

}
@Composable
fun SettingsGrid() {
    val settingsList = remember {
        mutableStateListOf(
            SettingItem("Enable Dark Mode", false),
            SettingItem("Enable Notifications", true),
            SettingItem("Biometric Authentication", false),
            SettingItem("Auto-update", true),
            SettingItem("Cloud Sync", false),
            SettingItem("Auto-correct", false),
            SettingItem("Show Hidden Notes", false),
            SettingItem("Developer Mode", false),
            SettingItem("TODO", false),
            SettingItem("TODO", false),

            )
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(900.dp)
            .padding(start = 10.dp,end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {

            Title("settings.")

        }
        items(settingsList.size) { index ->
            val setting = settingsList[index]
            val title = setting.title
            val shape = when (index) {
                0 -> RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)  // first item
                settingsList.size - 1 -> RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp) // last item
                else -> RoundedCornerShape(10.dp) // middle items
            }

                Box(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .clip(shape)

                        .background(MaterialTheme.colorScheme.secondary)
                        .border(
                            0.5.dp,
                            shape = shape,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                ){
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier

                            .padding(start = 20.dp,end = 40.dp)
                            .align(Alignment.CenterStart)
                    )
                    Switch(
                        modifier = Modifier
                            .padding(end = 40.dp)
                            .align(Alignment.CenterEnd),
                        checked = setting.isEnabled,
                        onCheckedChange = { checked ->
                            settingsList[index] = setting.copy(isEnabled = checked)

                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.background ,      // circle when ON
                            checkedTrackColor = MaterialTheme.colorScheme.primary, // track when ON
                            uncheckedThumbColor = Color.Gray,     // circle when OFF
                            uncheckedTrackColor = MaterialTheme.colorScheme.background // track when OFF

                    ))
                }

            }
        item {

           Box(
               modifier = Modifier
                   .height(100.dp)
                   .fillMaxWidth()

           ){
               val ver = AppVersionText()
               Text(
                   text = "noto.\nv"+ ver+" - dev",

                   color = Color.Gray,
                   modifier = Modifier

                       .padding(start = 20.dp,end = 40.dp)
                       .align(Alignment.CenterStart)
               )
           }


        }
}}
