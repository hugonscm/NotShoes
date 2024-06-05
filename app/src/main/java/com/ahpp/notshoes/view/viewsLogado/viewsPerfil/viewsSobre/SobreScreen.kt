package com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsSobre

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R

@Composable
fun SobreScreen(onBackPressed: () -> Unit) {

    val corPredominante = Color(0xFF029CCA)

    BackHandler {
        onBackPressed()
    }
    Column {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF029CCA))
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .size(45.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { onBackPressed() },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    Icons.Default.Close,
                    contentDescription = "Toque para voltar",
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                text = "Sobre o aplicativo",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 50.dp))

            Text(
                text = "NOTSHOES",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                color = corPredominante
            )

            AnimatedBorderCard(
                modifier = Modifier
                    .height(370.dp)
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(size = 10.dp),
                gradient = Brush.linearGradient(listOf(Color.Yellow, Color.Cyan)),
                borderWidth = 7.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        tint = corPredominante,
                        painter = painterResource(id = R.drawable.baseline_school_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 3.dp)
                    )
                    Text(
                        modifier = Modifier.width(250.dp),
                        text = "Aplicativo desenvolvido para disciplina de\nEngenharia de Software II.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = corPredominante
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        modifier = Modifier.width(250.dp),
                        text = "2024.1 - T01",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        color = corPredominante
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = 0.dp),
    borderWidth: Dp = 2.dp,
    gradient: Brush = Brush.sweepGradient(listOf(Color.Gray, Color.White)),
    animationDuration: Int = 3000,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    Surface(
        modifier = modifier
            .clip(shape),
        shape = shape
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(borderWidth)
                .drawWithContent {
                    rotate(degrees = degrees) {
                        drawCircle(
                            brush = gradient,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            color = Color.White,
            shape = shape
        ) {
            content()
        }
    }
}