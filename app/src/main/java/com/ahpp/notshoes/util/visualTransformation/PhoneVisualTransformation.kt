package com.ahpp.notshoes.util.visualTransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val phoneMask = text.text.mapIndexed{ index, char ->
            when(index){
                0 -> "($char"
                1 -> "$char) "
                6 -> "$char-"
                else -> char
            }
        }.joinToString(separator = "")

        return TransformedText(
            AnnotatedString(phoneMask),
            PhoneOffsetMapping
        )
    }

    object PhoneOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 6 -> offset + 4
                offset > 1 -> offset + 3
                offset > 0 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            //Log.e("offset", offset.toString())
            return when {
                offset >= 11 -> offset - 4
                offset >= 5 -> offset - 3
                offset == 4 -> offset - 2
                offset >= 2 -> offset - 1
                else -> offset
            }
        }
    }
}