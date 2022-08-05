package com.tzmax.abacus.ui.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout


@Composable
fun Grid(
    modifier: Modifier = Modifier,
    maxRows: Int,
    maxColumns: Int,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurableList, constraints ->
        check(constraints.hasBoundedWidth) {
            "no width"
        }
        val columnWidth = constraints.maxWidth / maxRows
        val columnHeight = constraints.maxHeight / maxColumns
        val itemConstraints = constraints.copy(
            minWidth = 0,
            maxWidth = columnWidth,
            minHeight = 0,
            maxHeight = columnHeight
        )
        val placeableList = measurableList.map { measurable ->
            measurable.measure(itemConstraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            placeableList.forEachIndexed { index, placeable ->
                placeable.place(
                    x = columnWidth * (index % maxRows),
                    y = columnHeight * (index / maxRows)
                )
            }
        }
    }
}