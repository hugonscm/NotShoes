package com.ahpp.notshoes.util.screensReutilizaveis

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.util.funcoes.possuiConexao

@Composable
fun SemConexaoScreen(onBackPressed: () -> Unit) {

    val ctx = LocalContext.current

    val color = remember { Animatable(Color.Gray) }
    LaunchedEffect(Unit) {
        color.animateTo(Color.Red, animationSpec = tween(500))
        color.animateTo(Color(0xFF38C4E6), animationSpec = tween(1000))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color.value
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Verifique sua conexão com internet e tente novamente.",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Button(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
                onClick = {
                    if (!possuiConexao(ctx)) {
                        Toast.makeText(
                            ctx,
                            "Sem conexão",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        onBackPressed()
                    }
                }
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_refresh_no_internet),
                    contentDescription = "Tentar novamente",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}