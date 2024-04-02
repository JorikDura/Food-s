package com.foods.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Dimension
import com.foods.R
import com.foods.domain.model.Meal
import com.foods.navigation.NavBarItem
import com.foods.navigation.NavigationItems
import com.foods.presentation.utils.EmptyBox
import com.foods.presentation.utils.LoadingBox


@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState(initial = MainScreenState())
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar()

        },
        bottomBar = {
            BottomBar()
        }
    ) { paddingValues ->
        MainContent(
            modifier = Modifier
                .padding(paddingValues),
            state = state,
            onEvent = { event ->
                viewModel.onEvent(event)
            }
        )
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    height: Dp = 56.dp
) {
    Row(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                text = stringResource(id = R.string.Moscow)
            )
            IconButton(onClick = { }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_down),
                    contentDescription = null
                )
            }
        }
        IconButton(onClick = { }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.qr_code),
                contentDescription = null
            )
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    height: Dp = 65.dp
) {
    Row(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_gray)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationItems.entries.forEach { navItem ->
            NavBarItem(
                modifier = Modifier
                    .weight(1f),
                isSelected = navItem == NavigationItems.Menu, //заглушка
                titleId = navItem.titleId,
                iconId = navItem.iconId
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onEvent: (MainScreenEvents) -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()

    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            onEvent(MainScreenEvents.Refresh)
        }
    }

    LaunchedEffect(state.isRefreshing) {
        if (!state.isRefreshing) {
            pullRefreshState.endRefresh()
        }
    }

    val columListState = rememberLazyListState()
    val rowListState = rememberLazyListState()

    val showShadowUnderTopApp by remember {
        derivedStateOf {
            columListState.firstVisibleItemIndex != 0
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        when {
            state.isLoading -> LoadingBox()

            state.isEmpty -> EmptyBox()

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = columListState
                ) {
                    item {
                        LazyRow(
                            modifier = Modifier
                                .height(120.dp)
                        ) {
                            items(items = state.banners) { bannerId ->
                                BannerItem(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp),
                                    bannerId = bannerId
                                )
                            }
                        }
                    }

                    stickyHeader {
                        LazyRow(
                            modifier = Modifier
                                .background(colorResource(id = R.color.gray_white)),
                            state = rowListState
                        ) {
                            items(items = state.tags, key = { it.id }) { tag ->
                                TagItem(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 24.dp),
                                    title = tag.title,
                                    isSelected = tag == state.currentTag,
                                    onClickListener = {
                                        onEvent(MainScreenEvents.ChangeTag(tag))
                                    }
                                )
                            }
                        }
                        if (showShadowUnderTopApp) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 8.dp,
                                    ),
                                color = Color.Transparent
                            )
                        }
                    }

                    if (state.meals.isNotEmpty()) {
                        items(items = state.meals, key = { it.id }) { meal ->
                            HorizontalDivider(color = colorResource(id = R.color.light))
                            FoodItem(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 16.dp),
                                meal = meal,
                                onClickListener = {
                                    onEvent(MainScreenEvents.BuyMeal(meal))
                                }
                            )
                        }
                    } else {
                        item {
                            EmptyBox()
                        }
                    }

                }
            }
        }
        val scaleFraction = if (pullRefreshState.isRefreshing) 1f else
            LinearOutSlowInEasing.transform(pullRefreshState.progress).coerceIn(0f, 1f)
        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp)
                .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
            state = pullRefreshState,
            contentColor = colorResource(id = R.color.pink),
            containerColor = colorResource(id = R.color.gray_white)
        )
        AnimatedVisibility(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            visible = state.isError,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Snackbar {
                Text(text = state.errorMessage)
            }
        }
    }
}

@Composable
fun FoodItem(
    modifier: Modifier = Modifier,
    meal: Meal,
    height: Dp = 150.dp,
    scale: ContentScale = ContentScale.Crop,
    onClickListener: () -> Unit
) {
    Row(
        modifier = modifier
            .height(height),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(0.4f),
            contentAlignment = Alignment.Center
        ) {
            val imagePainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(meal.imageUrl)
                    .crossfade(true)
                    .size(Dimension.Undefined, Dimension.Pixels(1920))
                    .build()
            )

            when (imagePainter.state) {
                AsyncImagePainter.State.Empty -> Unit

                is AsyncImagePainter.State.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.error_message))
                    }
                }

                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.pink)
                        )
                    }
                }

                is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp)),
                        painter = imagePainter,
                        contentDescription = null,
                        contentScale = scale
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(0.6f)
                .fillMaxSize()
        ) {
            Column {
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    text = meal.title
                )
                Text(
                    fontSize = 14.sp,
                    text = meal.ingredients,
                    color = Color.Gray
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                border = BorderStroke(1.dp, colorResource(id = R.color.pink)),
                onClick = { onClickListener() }
            ) {
                Text(
                    fontSize = 13.sp,
                    color = colorResource(id = R.color.pink),
                    text = meal.cost
                )
            }
        }
    }
}

@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    onClickListener: () -> Unit
) {
    Box(
        modifier = modifier
            .height(35.dp)
            .width(90.dp)
            .shadow(6.dp, RoundedCornerShape(5.dp))
            .background(
                if (isSelected) colorResource(id = R.color.light_pink)
                else Color.White
            )
            .clickable { onClickListener() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            fontSize = 13.sp,
            color = if (isSelected) colorResource(id = R.color.pink)
            else Color.LightGray,
            text = title
        )
    }
}

@Composable
fun BannerItem(
    modifier: Modifier = Modifier,
    bannerId: Int,
    scale: ContentScale = ContentScale.Crop
) {
    Image(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxHeight()
            .width(300.dp),
        painter = painterResource(id = bannerId),
        contentDescription = null,
        contentScale = scale
    )
}
