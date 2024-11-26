package com.example.weatherforecast.ui.features.daysForecastScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weatherforecast.R
import com.example.weatherforecast.constants.SCHEME
import com.example.weatherforecast.ui.theme.CardBg

@Preview(showBackground = true)
@Composable
fun DaysScreen(viewModel: DaysForecastViewModel = hiltViewModel()) {
    val state by viewModel.state.observeAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = state?.daysForecast ?: arrayListOf()
        ) { item ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = CardBg
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                ) {
                    Text(
                        text = item.date,
                        color = Color.DarkGray,
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.condition,
                            color = Color.White,
                            style = TextStyle(fontSize = 18.sp)
                        )
                        Text(
                            text = stringResource(
                                id = R.string.dateHours,
                                item.maxTemp,
                                item.mintTemp
                            ),
                            color = Color.White,
                            style = TextStyle(fontSize = 24.sp),
                            modifier = Modifier.padding(start = 100.dp)
                        )
                    }
                    AsyncImage(
                        model = "$SCHEME${item.imageUrl}",
                        placeholder = painterResource(id = R.drawable.sunny),
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(
                                start = 10.dp,
                                top = 10.dp
                            )
                    )
                    Text(
                        text = stringResource(id = R.string.rain_field, item.rainChance),
                        color = Color.DarkGray,
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(
                                end = 10.dp,
                                bottom = 5.dp
                            )
                    )
                }
            }
        }
    }
}
