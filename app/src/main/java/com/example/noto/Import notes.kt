package com.example.noto

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke

import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp


@SuppressLint("SuspiciousModifierThen")
fun Modifier.dottedBorder(
    strokeWidth: Dp = 4.dp,
    color: Color = Color.Gray,
    dotLength: Dp = 8.dp,
    gapLength: Dp = 6.dp,
    cornerRadius: Dp = 20.dp
): Modifier = this.then(
    drawBehind {
        val pxStroke = strokeWidth.toPx()
        val pxDot = dotLength.toPx()
        val pxGap = gapLength.toPx()
        val pxRadius = cornerRadius.toPx()
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(pxRadius, pxRadius),
            style = Stroke(
                width = pxStroke,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(pxDot, pxGap), 0f)
            )
        )
    }
)
@Composable
fun ImportBox(){

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(

            modifier = Modifier

                .align(alignment = Alignment.Center)
                .fillMaxWidth(0.9F)
                .height(320.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .dottedBorder()



        ){
            Icon(
                painter = painterResource(R.drawable.outline_file_upload_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .fillMaxSize(0.4f)

            )
        }
    }

}