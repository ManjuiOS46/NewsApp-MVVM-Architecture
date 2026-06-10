package com.joydipbhakat.newsapp.ui.language

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.ui.UIState
import com.joydipbhakat.newsapp.ui.component.ErrorScreen
import com.joydipbhakat.newsapp.ui.component.LoadingScreen
import com.joydipbhakat.newsapp.ui.component.NewsListScreen


@Composable
fun LanguageScreen() {
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val languages = languageViewModel.languages.collectAsState().value
    val data = languages.map { it.second }
    val selectedLanguage = remember { mutableStateListOf<String>() }
    var showNewsScreen by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        if (showNewsScreen && selectedLanguage.size == 2) {
            val first = languages.find { it.second == selectedLanguage[0] }?.first
            val second = languages.find { it.second == selectedLanguage[1] }?.first
            LanguageNewsScreen(first, second) {
                 showNewsScreen = false
                 selectedLanguage.clear()
            }
        } else {
            Column {
                Text(
                    text = stringResource(id = R.string.languages),
                    modifier = Modifier.padding(8.dp, 16.dp, 8.dp, 8.dp),
                    fontSize = 32.sp
                )
                LazyColumn {
                    items(data) { item ->
                        val isSelected = item in selectedLanguage
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {

                            if (isSelected) {
                                Icon(
                                    painter = painterResource(id = R.drawable.check),
                                    contentDescription = "Selected",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .padding(end = 12.dp)
                                )
                            } else {
                                Spacer(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 12.dp)
                                )
                            }

                            Button(
                                onClick = {
                                    if (isSelected) {
                                        selectedLanguage.remove(item)
                                    } else {
                                        if (selectedLanguage.size < 2) {
                                            selectedLanguage.add(item)
                                        }
                                    }
                                    if (selectedLanguage.size == 2) {
                                        showNewsScreen = true
                                    }
                                },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.purple_200)
                                ),
                                shape = RectangleShape
                            )
                            {
                                Text(text = item)
                            }
                        }

                    }
                }
            }
        }

    }
}

@Composable
fun LanguageNewsScreen(firstLanguageSelected: String?, secondLanguageSelected: String?,onBackPressed :() -> Unit) {
    val viewModel: LanguageViewModel = hiltViewModel()
    val context = LocalContext.current

    BackHandler {
        onBackPressed()
    }
    LaunchedEffect(firstLanguageSelected, secondLanguageSelected) {
        if (firstLanguageSelected != null && secondLanguageSelected != null) {
            viewModel.getNewsBasedOnLanguage(firstLanguageSelected, secondLanguageSelected)
        }
    }
    when (val uiState = viewModel.uiStateForLanguage.collectAsState().value) {
        is UIState.Success -> {
            NewsListScreen(data = uiState.data) { url ->
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()
                    .launchUrl(
                        context,
                        Uri.parse(url)
                    )
            }
        }
        is UIState.Error -> {
            ErrorScreen {
                viewModel.getNewsBasedOnLanguage(
                    firstLanguageSelected.toString(),
                    secondLanguageSelected.toString()
                )
            }
        }
        is UIState.Loading -> {
            LoadingScreen()
        }
    }
}