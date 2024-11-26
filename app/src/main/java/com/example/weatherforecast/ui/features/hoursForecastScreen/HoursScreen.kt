package com.example.weatherforecast.ui.features.hoursForecastScreen

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
fun HoursScreen(viewModel: HoursViewModel = hiltViewModel()) {
    val state by viewModel.state.observeAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = state?.hoursForecast ?: arrayListOf()
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                    ) {
                        Text(
                            text = item.time,
                            style = TextStyle(fontSize = 16.sp)
                        )
                        Text(
                            text = item.condition,
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.hoursTemp, item.currentTemp),
                        color = Color.White,
                        style = TextStyle(fontSize = 18.sp)
                    )
                    AsyncImage(
                        model = "$SCHEME${item.imageUrl}",
                        placeholder = painterResource(id = R.drawable.sunny),
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 10.dp)
                    )
                }
            }
        }
    }
}