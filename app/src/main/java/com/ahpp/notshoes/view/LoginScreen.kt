package com.ahpp.notshoes.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.ahpp.notshoes.R

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //gerenciar visibilidade da senha
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.baseline_visibility_24)
    else
        painterResource(id = R.drawable.baseline_visibility_off_24)

    Column(
        modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFFFFFFF),
                        Color(0xFFFFFFFF),
                        Color(0xFF86D0E2),
                        Color(0xFF029CCA)
                    )
                )
            )
            .padding(start = 20.dp, end = 20.dp)
    ) {

        Row(
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 80.dp)
        ) {
            Text(text = "NotShoes", fontSize = 44.sp, fontWeight = FontWeight.Bold)
        }

        Row(modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Entre e aproveite as nossas ofertas!", fontSize = 20.sp)
        }

        Row(modifier.padding(top = 35.dp)) {
            Text(text = "E-mail", fontWeight = FontWeight.Bold)
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(text = "email@exemplo.com", color = Color(0xFF4A5255)) },
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = "Icone email", tint = Color.Black)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEEF3F5),
                focusedContainerColor= Color(0xFFEEF3F5),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color(0xFF029CCA),
                focusedLabelColor = Color(0xFF000000),
                cursorColor = Color(0xFF029CCA),
            )
        )

        Row(modifier.padding(top = 10.dp)) {
            Text(text = "Senha", fontWeight = FontWeight.Bold)
        }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            //label = { Text("Senha") },
            placeholder = { Text(text = "Digite sua senha", color = Color(0xFF4A5255)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = "Icone senha", tint = Color.Black)
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon", tint = Color.Black
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEEF3F5),
                focusedContainerColor= Color(0xFFEEF3F5),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color(0xFF029CCA),
                focusedLabelColor = Color(0xFF000000),
                cursorColor = Color(0xFF029CCA),
            )
        )

        Row(
            modifier
                .padding(top = 13.dp)
                .align(Alignment.End)
        ) {
            Text(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = { }),
                text = "Esqueceu a senha?",
                fontSize = 16.sp,
                style = TextStyle(
                    Color(0xFF029CCA)
                )
            )
        }

        Row(
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 25.dp)
        ) {
            ElevatedButton(
                onClick = { navController.navigate("homeController") { launchSingleTop = true } },
                modifier
                    .width(230.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF029CCA))
            ) {
                Text(
                    text = "Entrar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        Color.White
                    )
                )
            }
        }

        Row(
            modifier
                .padding(top = 25.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Não tem uma conta? ",
                fontSize = 15.sp,
                style = TextStyle(
                    Color.Black
                )
            )
            Text(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = { navController.navigate("registro") { launchSingleTop = true } }),
                text = "Cadastre-se",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                style = TextStyle(
                    Color(0xFF029CCA)
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Image(
                painter = painterResource(id = R.drawable.img_login),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 3.dp)
            )
        }

    }
}