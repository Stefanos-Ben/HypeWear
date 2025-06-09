package com.stephben.hypewear.apparel.presentation.search.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.stephben.hypewear.R

@Composable
fun FilterButtonWithBadge(
    activeCount: Int,
    onClick: () -> Unit
) {
    BadgedBox(
        badge = {
            if (activeCount > 0) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ) {
                    Text(text = activeCount.toString())
                }
            }
        }
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(R.drawable.baseline_filter_list_alt_24),
                contentDescription = stringResource(R.string.filter_hint),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}