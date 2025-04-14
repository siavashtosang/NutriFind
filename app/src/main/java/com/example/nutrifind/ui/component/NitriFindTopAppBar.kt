package com.example.nutrifind.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.example.nutrifind.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriFindTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    showTitle: Boolean = false,
    actions: @Composable() (RowScope.() -> Unit) = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavigationIconClick: () -> Unit,

    ) {
    TopAppBar(
        title = {
            AnimatedVisibility(visible = showTitle,
                enter = slideIn(
                    animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing),
                    initialOffset = { IntOffset(it.width / 4, 100) }
                ),
                exit = slideOut(
                    animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
                    targetOffset = { IntOffset(-180, 50) }
                )
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall,
                )
            }

        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { onNavigationIconClick() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_24),
                    contentDescription = "ic_arrow_left_24",
                    tint = MaterialTheme.colorScheme.outline
                )
            }

        },
        actions = actions,
        windowInsets = windowInsets,
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = TopAppBarDefaults.topAppBarColors().scrolledContainerColor,
            navigationIconContentColor = TopAppBarDefaults.topAppBarColors().navigationIconContentColor,
            titleContentColor = TopAppBarDefaults.topAppBarColors().titleContentColor,
            actionIconContentColor = TopAppBarDefaults.topAppBarColors().actionIconContentColor
        ),
        scrollBehavior = scrollBehavior,
    )
}