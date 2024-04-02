package com.foods.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.foods.R

@Composable
fun EmptyBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray_white)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            fontSize = 18.sp,
            color = colorResource(id = R.color.gray),
            text = stringResource(id = R.string.empty)
        )
    }
}