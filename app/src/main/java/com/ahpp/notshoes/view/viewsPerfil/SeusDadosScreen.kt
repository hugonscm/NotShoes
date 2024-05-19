package com.ahpp.notshoes.view.viewsPerfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.view.viewsPerfil.viewsSeusDados.AlterarDadosPessoaisScreen
import com.ahpp.notshoes.view.viewsPerfil.viewsSeusDados.AlterarEmailScreen
import com.ahpp.notshoes.view.viewsPerfil.viewsSeusDados.AlterarSenhaScreen

@Composable
fun SeusDadosScreen(onBackPressed: () -> Unit) {

    var clickedAlterarDadosPessoais by remember { mutableStateOf(false) }
    var clickedAlterarEmail by remember { mutableStateOf(false) }
    var clickedAlterarSenha by remember { mutableStateOf(false) }

    if (clickedAlterarDadosPessoais) {
        AlterarDadosPessoaisScreen(onBackPressed = { clickedAlterarDadosPessoais = false })
    } else if (clickedAlterarEmail) {
        AlterarEmailScreen(onBackPressed = { clickedAlterarEmail = false })
    } else if (clickedAlterarSenha) {
        AlterarSenhaScreen(onBackPressed = { clickedAlterarSenha = false })
    } else {
        MenuSeusDados(
            onBackPressed = onBackPressed,
            onClickAlterarDadosPessoais = { clickedAlterarDadosPessoais = true },
            onClickAlterarEmail = { clickedAlterarEmail = true },
            onclickAlterarSenha = { clickedAlterarSenha = true })
    }
}

@Composable
fun MenuSeusDados(
    onBackPressed: () -> Unit,
    onClickAlterarDadosPessoais: () -> Unit,
    onClickAlterarEmail: () -> Unit,
    onclickAlterarSenha: () -> Unit
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
                text = "Seus dados", fontSize = 20.sp, maxLines = 1,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    Color(0xFFFFFFFF)
                )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable(true, onClick = onClickAlterarDadosPessoais),
                shape = RoundedCornerShape(0.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(start = 10.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alterar dados pessoais",
                        fontSize = 18.sp
                    )
                    Image(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(18.dp),
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable(true, onClick = onClickAlterarEmail),
                shape = RoundedCornerShape(0.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(start = 10.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alterar e-mail",
                        fontSize = 18.sp
                    )
                    Image(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(18.dp),
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable(true, onClick = onclickAlterarSenha),
                shape = RoundedCornerShape(0.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(start = 10.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alterar senha",
                        fontSize = 18.sp
                    )
                    Image(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(18.dp),
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }
    }
}