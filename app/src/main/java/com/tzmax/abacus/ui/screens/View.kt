package com.tzmax.abacus.ui.screens

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.tzmax.abacus.R
import com.tzmax.abacus.ui.theme.Gap
import com.tzmax.abacus.ui.theme.colors
import com.tzmax.abacus.ui.model.AbacusViewModel
import com.tzmax.abacus.ui.components.Grid


@Composable
fun AppView(
    vm: AbacusViewModel
) {
    //当前计算的算式
    val formula = remember { vm.formula }
    //历史记录
    val history = remember { vm.history }
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        ScreenPart(
            modifier = Modifier.weight(1f),
            formula = formula,
            history = history
        )
        OperationPart(Modifier.weight(1f), formula = formula, history = history)
    }

}

@Composable
private fun ScreenPart(
    modifier: Modifier = Modifier,
    formula: MutableState<String>,
    history: SnapshotStateList<String>
) {
    Column(
        modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 22.sp,
            color = colors.onBackground,
            modifier = Modifier.padding(horizontal = Gap.Big, vertical = Gap.Mid)
        )
        LazyColumn(
            Modifier
                .background(colors.background)
                .fillMaxWidth()
                .weight(1f),
            reverseLayout = true,
            contentPadding = PaddingValues(horizontal = Gap.Big, vertical = Gap.Big)
        ) {
            item {
                Text(
                    text = formula.value,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 60.sp,
                    color = colors.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }
            items(history.reversed()) {
                Text(
                    text = it,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 16.sp,
                    color = colors.onSecondary,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun OperationPart(
    modifier: Modifier = Modifier,
    formula: MutableState<String>,
    history: SnapshotStateList<String>
) {
    Grid(
        modifier = modifier, maxRows = 4, maxColumns = 5
    ) {
        buttons.forEach {
            GoButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f, true),
                text = stringResource(it.text),
                fontColor = when (it.fontColor) {
                    ColorType.Accent -> colors.primary//文字强调色
                    ColorType.Normal -> colors.onBackground
                    ColorType.OnAccent -> colors.onPrimary
                },
                backgroundColor = when (it.backgroundColor) {
                    ColorType.Accent -> colors.primary
                    else -> colors.background
                },
            ) {
                it.onClick(formula) { new ->
                    //提交计算结果，将其纳入历史记录
                    if (new != null) {
                        history.add(new)
                    } else {
                        //清空历史记录
                        history.clear()
                    }
                }
            }
        }
    }
}

@Composable
fun GoButton(
    modifier: Modifier,
    text: String,
    fontColor: Color = colors.onBackground,
    backgroundColor: Color = colors.background,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        Modifier
            .clickable(
                onClickLabel = text,
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            )
            .then(modifier), contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .padding(horizontal = Gap.Mid, vertical = Gap.Small)
                .aspectRatio(1f, true)
                .clip(RoundedCornerShape(50))
                .background(
                    backgroundColor, RoundedCornerShape(50)
                )
                .clickable(
                    onClickLabel = text,
                    onClick = onClick,
                    indication = LocalIndication.current,
                    interactionSource = interactionSource
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = fontColor,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
