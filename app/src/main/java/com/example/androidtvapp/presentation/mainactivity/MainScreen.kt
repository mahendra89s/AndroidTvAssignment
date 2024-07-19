package com.example.androidtvapp.presentation.mainactivity

import android.graphics.BitmapFactory
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import androidx.tv.material3.AssistChipDefaults
import androidx.tv.material3.Carousel
import androidx.tv.material3.CarouselDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.ShapeDefaults
import androidx.tv.material3.Text
import androidx.tv.material3.rememberCarouselState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidtvapp.R
import com.example.androidtvapp.data.repository.model.TopHeadlineArticleBM
import com.example.androidtvapp.presentation.mainactivity.component.CountryDropDown
import com.example.androidtvapp.presentation.mainactivity.model.MainUi
import com.example.androidtvapp.presentation.mainactivity.model.MainViewEvent
import com.example.androidtvapp.presentation.mainactivity.model.MainViewState
import com.example.androidtvapp.utils.dateTimeFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit) {
        mainViewModel.handleEvent(MainViewEvent.OnScreenLoad)
    }
    val uiState by mainViewModel.viewState.collectAsState()

    MainView(
        uiState = uiState,
        onRetry = {
            mainViewModel.handleEvent(MainViewEvent.OnScreenLoad)
        },
        onCategorySelect = {
            mainViewModel.handleEvent(MainViewEvent.OnCategorySelected(it))
        },
        onCountrySelected = {
            mainViewModel.handleEvent(MainViewEvent.OnCountrySelected(it))
        }
    )
}

@OptIn(ExperimentalTvMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainView(
    uiState: MainViewState,
    onRetry: () -> Unit,
    onCategorySelect: (String) -> Unit,
    onCountrySelected: (String) -> Unit
) {
    val carouselState = rememberCarouselState()
    var isCarouselFocused by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var dominantColor by remember {
        mutableStateOf(Color(0xFF3e3e42))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF3e3e42),
                        Color(0xFF3e3e42),
                        dominantColor,
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
    ) {
        when (val viewState = uiState.mainUi) {
            is MainUi.Success -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .padding(horizontal = 30.dp)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        items(
                            count = viewState.categories.size,
                        ) {
                            AssistChip(
                                modifier = Modifier
                                    .padding(start = 10.dp),
                                onClick = {
                                    onCategorySelect(viewState.categories[it])
                                },
                                label = {
                                    Text(
                                        viewState.categories[it],
                                        color = Color.White
                                    )
                                },
                                leadingIcon = {
                                },
                            )
                        }

                    }

                    CountryDropDown(
                        selectedCountry = viewState.selectedCountry,
                        countryList = viewState.countries,
                        onCountrySelected = onCountrySelected
                    )

                }



                Carousel(
                    itemCount = viewState.article.size,
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .padding(horizontal = 30.dp)
                        .clip(ShapeDefaults.Medium)
                        .border(
                            width = 4.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (isCarouselFocused) 1f else 0f),
                            shape = ShapeDefaults.Medium,
                        )
                        .onFocusChanged {
                            isCarouselFocused = it.hasFocus
                        },
                    carouselState = carouselState,
                    carouselIndicator = {
                        CarouselDefaults.IndicatorRow(
                            itemCount = viewState.article.size,
                            activeItemIndex = carouselState.activeItemIndex,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(vertical = 16.dp, horizontal = 30.dp),
                        )
                    },
                    contentTransformEndToStart =
                    fadeIn(tween(1000)).togetherWith(fadeOut(tween(1000))),
                    contentTransformStartToEnd =
                    fadeIn(tween(1000)).togetherWith(fadeOut(tween(1000)))
                ) { itemIndex ->
                    LaunchedEffect(key1 = carouselState) {
                        scope.launch(
                            context = Dispatchers.IO
                        ) {
                            try {
                                val bitmap =
                                    BitmapFactory.decodeStream(URL(viewState.article[itemIndex].urlToImage).openStream())
                                bitmap?.let {
                                    Palette.Builder(bitmap).generate {
                                        it?.let { palette ->
                                            dominantColor =
                                                palette.darkVibrantSwatch?.rgb?.let { it1 ->
                                                    Color(
                                                        it1
                                                    )
                                                } ?: Color(0xFF3e3e42)
                                        }
                                    }
                                } ?: run {
                                    dominantColor = Color(0xFF3e3e42)
                                }
                            } catch (e: Exception) {
                                dominantColor = Color(0xFF3e3e42)
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(viewState.article[itemIndex].urlToImage)
                                .build(),
                            contentDescription = stringResource(R.string.app_name),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF252526)),
                            alignment = Alignment.CenterEnd
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xff252526),
                                            Color(0xff252526),
                                            Color(0x00000000),
                                            Color(0x00000000),
                                            Color(0x00000000),
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.BottomStart
                        ) {

                        }

                        /* Content */
                        CarouselContentBlock(
                            item = viewState.article[itemIndex],
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 60.dp, end = 30.dp, bottom = 30.dp)
                                .fillMaxWidth(0.8f)
                                .animateEnterExit(
                                    enter = slideInHorizontally(animationSpec = tween(1000)) { it / 2 },
                                    exit = slideOutHorizontally(animationSpec = tween(1000))
                                )
                        )

                    }
                }
            }


            is MainUi.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewState.message,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        onRetry()
                    }) {
                        Text(
                            text = stringResource(id = R.string.retry),
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }

            is MainUi.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xff252526)
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun CarouselContentBlock(
    item: TopHeadlineArticleBM,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = item.title ?: "",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(top = 7.dp),
            text = item.description ?: "",
            color = Color.White,
            fontSize = 18.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis
        )
        item.sourceRM?.let {
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = "Source : ${it.name ?: ""}",
                color = Color(0xFF999999),
                fontSize = 16.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = item.publishedAt?.dateTimeFormatter() ?: "",
            color = Color(0xFF999999),
            fontSize = 16.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis
        )
    }
}

