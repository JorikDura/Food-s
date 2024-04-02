package com.foods.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.foods.R

enum class NavigationItems(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
) {
    Menu(titleId = R.string.menu, iconId = R.drawable.menu),
    Profile(titleId = R.string.profile, iconId = R.drawable.profile),
    Cart(titleId = R.string.cart, iconId = R.drawable.cart)
}