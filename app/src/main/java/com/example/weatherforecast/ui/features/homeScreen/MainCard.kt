package com.example.weatherforecast.ui.features.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherforecast.R
import com.example.weatherforecast.ui.theme.CardBg
import com.example.weatherforecasts.ui.models.CurrentDayModel

@Composable
fun MainCard(weather: CurrentDayModel, onClickSync: () -> Unit, onClickSearch: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CardBg
        ),
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                    text = weather.dateTime,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )
                AsyncImage(
                    model = "https:${weather.imageUrl}",
                    placeholder = painterResource(id = R.drawable.sunny),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 10.dp, top = 10.dp)
                )
            }
            Text(
                text = weather.city,
                style = TextStyle(fontSize = 24.sp),
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.hoursTemp, weather.currentTemp),
                style = TextStyle(fontSize = 70.sp),
                color = Color.White
            )
            Text(
                text = weather.condition,
                style = TextStyle(fontSize = 16.sp),
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onClickSearch.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Text(
                    text = stringResource(
                        id = R.string.dateHours,
                        weather.maxTemp,
                        weather.mintTemp
                    ),
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White,
                    modifier = Modifier.padding(top = 5.dp)
                )
                IconButton(onClick = { onClickSync.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sync),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}