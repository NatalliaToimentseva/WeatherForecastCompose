package com.example.weatherforecast.ui.features.homeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherforecast.R
import com.example.weatherforecast.ui.features.daysForecastScreen.DaysScreen
import com.example.weatherforecast.ui.features.homeScreen.domain.HomeAction
import com.example.weatherforecast.ui.features.hoursForecastScreen.HoursScreen
import com.example.weatherforecast.ui.theme.CardBg
import com.example.weatherforecasts.constants.CITY
import com.example.weatherforecasts.ui.models.CurrentDayModel
import com.example.weatherforecasts.utils.makeToast
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.observeAsState()
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val pagerState =
        rememberPagerState(initialPage = state?.initialPage ?: 0) { state?.tabsName?.size ?: 0 }
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        locationPermissions.launchMultiplePermissionRequest()
    }

    LaunchedEffect(Unit) {
        viewModel.processAction(HomeAction.GetLocation)
    }

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f),
        contentScale = ContentScale.FillBounds
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            MainCard(
                state?.currentDayWeather ?: CurrentDayModel("None", "", "", "", "", "", ""),
                { viewModel.processAction(HomeAction.GetWeatherData(CITY)) },
                { viewModel.processAction(HomeAction.ShowDialog) })
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                containerColor = CardBg,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                state?.tabsName?.forEachIndexed { index, text ->
                    Tab(selected = selectedTabIndex.value == index,
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.DarkGray,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(text = text)
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1.0f),
            ) { page ->
                if (page == 0) {
                    HoursScreen()
                } else {
                    DaysScreen()
                }
            }
        }
        if (state?.isInProgress == true) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(50.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
        state?.errorsGettingData?.let {
            LocalContext.current.makeToast(it)
            viewModel.processAction(HomeAction.ClearError)
        }

        if (state?.isShowDialog == true) {
            AlertDialog(
                onDismissRequest = { viewModel.processAction(HomeAction.HideDialog) },
                confirmButton = {
                    TextButton(onClick = {
                        state?.dialogText?.let { text ->
                            if (text.isNotBlank()) viewModel.processAction(
                                HomeAction.GetWeatherData(text)
                            )
                        }
                        viewModel.processAction(HomeAction.HideDialog)
                    }) {
                        Text(text = stringResource(id = R.string.positive_button))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.processAction(HomeAction.HideDialog) }) {
                        Text(text = stringResource(id = R.string.negative_button))
                    }
                },
                title = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(id = R.string.dialog_title),
                            style = TextStyle(fontSize = 18.sp),
                            color = Color.DarkGray
                        )
                        Text(
                            text = stringResource(id = R.string.dialog_message),
                            style = TextStyle(fontSize = 16.sp),
                            color = Color.DarkGray,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                        state?.dialogText?.let {
                            TextField(value = it,
                                onValueChange = { city ->
                                    viewModel.processAction(HomeAction.SetCityFromDialog(city))
                                })
                        }
                    }
                },
            )
        }
    }
}


