package com.nagane.table.ui.screen.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nagane.table.data.model.Category
import com.nagane.table.ui.screen.home.MenuViewModel
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_8
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub

@Composable
fun CategoryRow(
    menuViewModel: MenuViewModel = viewModel()
) {
    val categories by menuViewModel.categories
    var nowSelected by remember { mutableStateOf(0L) }

    LazyRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            CategoryButton(
                category,
                nowSelected = nowSelected,
                onClick = { categoryNo ->
                    nowSelected = categoryNo
                    menuViewModel.fetchMenus(categoryNo)
                }
            )
        }
    }
}

@Preview
@Composable
fun CategoryButton(
    category: Category = Category(0, "전체보기"),
    nowSelected: Long = 0,
    onClick: (Long) -> Unit = {},
) {
    Button(
        modifier = Modifier
            .height(56.dp),
        onClick = {
            onClick(category.categoryNo)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (nowSelected.toLong() == category.categoryNo) nagane_theme_main else Color.Transparent,
            contentColor = nagane_theme_light_0,
            disabledContainerColor = nagane_theme_light_8,
            disabledContentColor = nagane_theme_light_0
        ),
        border = BorderStroke(2.dp, nagane_theme_main),
    ) {
        Text(
            text = category.categoryName,
            style = NaganeTypography.h2,
            color = if (nowSelected.toLong() == category.categoryNo) nagane_theme_sub else nagane_theme_main
        )
    }
}