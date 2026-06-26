package com.example.noto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke

import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius


@SuppressLint("SuspiciousModifierThen")
fun Modifier.dottedBorder(
    strokeWidth: Dp = 5.dp,
    color: Color = Color.Gray,
    dotLength: Dp = 8.dp,
    gapLength: Dp = 6.dp,
    cornerRadius: Dp = 16.dp
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
                .dottedBorder()
                .padding(all = 10.dp)
                .align(alignment = androidx.compose.ui.Alignment.Center)
                .fillMaxWidth(0.9F)
                .height(300.dp)


        )
    }

}