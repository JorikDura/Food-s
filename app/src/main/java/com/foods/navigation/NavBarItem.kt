package com.foods.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.foods.R

@Composable
fun NavBarItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    titleId: Int,
    iconId: Int
) {
    Column(
        modifier = modifier
            .clip(CircleShape),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val color =
            if (isSelected) colorResource(id = R.color.pink)
            else colorResource(id = R.color.gray)

        Icon(
            imageVector = ImageVector.vectorResource(iconId),
            contentDescription = null,
            tint = color
        )

        Text(
            fontSize = 12.sp,
            color = color,
            text = stringResource(id = titleId)
        )
    }
}