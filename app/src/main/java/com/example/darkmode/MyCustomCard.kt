package com.example.darkmode

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.darkmode.ui.theme.White80

data class Publisher (
    val name: String,
    @DrawableRes val image: Int,
    val job: String
)

@Composable
fun MyCustomCard(
    modifier: Modifier = Modifier,
    publisher: Publisher,
    text: String,
) {

    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        // Columna del contenido de la card
        Column (modifier = Modifier.padding (vertical = 20.dp, horizontal = 15.dp), horizontalAlignment = Alignment.CenterHorizontally){
            //Columna de la informacion del publisher
            Column (modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Image (
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = publisher.image),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(10.dp))
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontSize = 18.sp
                        )
                    ){
                        append(publisher.name)
                    }
                    append("\n")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onSecondary.copy (alpha = 0.6f),
                            fontSize = 16.sp,
                        )
                    ) {
                        append(publisher.job)
                    }
                }
                Text(text = annotatedString, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSecondary) // texto del nombre y del cargo
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSecondary.copy (alpha= 0.8f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,

            )
        }
    }
}