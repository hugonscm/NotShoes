package com.ahpp.notshoes.view.viewsPerfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R

@Composable
fun SobreScreen(onBackPressed: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
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
                    painterResource(id = R.drawable.baseline_close_24),
                    contentDescription = "Toque para voltar",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
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
                textAlign = TextAlign.Center
//                style = TextStyle(
//                    Color(0xFF029CCA)
//                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.width(250.dp),
                text = "2024.1 - T01",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center
//                style = TextStyle(
//                    Color(0xFF029CCA)
//                )
            )
        }
    }
}