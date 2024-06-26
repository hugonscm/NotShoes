package com.ahpp.notshoes.util

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.ahpp.notshoes.ui.theme.azulEscuro
import com.ahpp.notshoes.ui.theme.branco

@Composable
fun RadioButtonButtonPersonalizado(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) azulEscuro else branco
    val contentColor = if (isSelected) Color.White else Color.Black

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonColors(backgroundColor, contentColor, contentColor, contentColor)
        )
        Text(text = text)
    }
}