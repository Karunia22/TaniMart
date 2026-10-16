package com.example.tanimart.ui.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.home.data.QuickMenu

@Composable
fun TaniMartQuickMenu(
    onMenuClick: (QuickMenu) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickMenu.entries.forEach { menu ->
            TaniMartQuickMenuItem(
                menu = menu,
                onClick = {
                    onMenuClick(menu)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TaniMartQuickMenuItem(
    menu: QuickMenu,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = menu.icon,
                contentDescription = menu.label
            )

            Text(
                text = menu.label,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}