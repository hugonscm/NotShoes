package com.ahpp.notshoes.util.visualTransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CpfVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val cpfMask = text.text.mapIndexed{ index, char ->
            when(index){
                2 -> "$char."
                5 -> "$char."
                8 -> "$char-"
                else -> char
            }
        }.joinToString(separator = "")

        return TransformedText(
            AnnotatedString(cpfMask),
            CpfOffsetMapping
        )
    }

    object CpfOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 8 -> offset + 3
                offset > 5 -> offset + 2
                offset > 2 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            //Log.e("offset", offset.toString())
            return when {
                offset >= 12 -> offset - 3
                offset >= 8 -> offset - 2
                offset >= 4 -> offset - 1
                else -> offset
            }
        }
    }
}