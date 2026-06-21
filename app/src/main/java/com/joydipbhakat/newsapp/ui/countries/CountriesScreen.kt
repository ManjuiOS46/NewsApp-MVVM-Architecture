package com.joydipbhakat.newsapp.ui.countries

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.data.network.models.Countries
import com.joydipbhakat.newsapp.ui.UIState
import com.joydipbhakat.newsapp.ui.component.ErrorScreen
import com.joydipbhakat.newsapp.ui.component.LoadingScreen
import com.joydipbhakat.newsapp.ui.component.NewsListScreen


@Composable
fun CountriesScreen() {
    val viewModel: CountriesViewModel = hiltViewModel()
    val countries = viewModel.countries.collectAsState().value
    val selectedCountry = viewModel.selectedCountry.collectAsState().value
    if (selectedCountry != null) {
        BackHandler {
            viewModel.clearSelectedCountry()
        }
        CountryNewsScreen(
            countryCode = selectedCountry,
            viewModel = viewModel
        )
    } else {
        CountryListScreen(
            countries = countries,
            onCountryClick = { code -> viewModel.onCountrySelected(code) }
        )
    }
}

@Composable
private fun CountryListScreen(
    countries: List<Countries>,
    onCountryClick: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = stringResource(id = R.string.countries),
                modifier = Modifier.padding(8.dp, 16.dp, 8.dp, 8.dp),
                fontSize = 32.sp
            )
            LazyColumn {
                items(countries) { item ->
                    Button(
                        onClick = { onCountryClick(item.code) },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(250.dp)
                            .padding(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.purple_200)
                        ),
                        shape = RectangleShape,
                    ) {
                        Text(text = item.name)
                    }
                }
            }
        }
    }
}

@Composable
private fun CountryNewsScreen(
    countryCode: String,
    viewModel: CountriesViewModel
) {
    val context = LocalContext.current

    when (val uiState = viewModel.uiStateForCountry.collectAsState().value) {
        is UIState.Success -> {
            NewsListScreen(data = uiState.data) { url ->
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()
                    .launchUrl(context, Uri.parse(url))
            }
        }
        is UIState.Error -> {
            ErrorScreen {
                viewModel.getTopHeadlinesBasedOnCountry(countryCode)
            }
        }
        is UIState.Loading -> LoadingScreen()
    }
}

