package com.ahpp.notshoes.view.viewsPerfil.viewsEnderecos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnderecosScreen(
    onBackPressed: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF029CCA))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.Bottom), contentPadding = PaddingValues(0.dp),
                onClick = { onBackPressed() },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Toque para voltar",
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(270.dp),
                text = "Seus endereços", fontSize = 20.sp, maxLines = 1,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    Color(0xFFFFFFFF)
                )
            )
        }
    }
}