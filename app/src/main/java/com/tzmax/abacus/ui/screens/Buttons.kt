package com.tzmax.abacus.ui.screens

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import com.tzmax.abacus.R
import com.tzmax.abacus.utils.*

data class ButtonData(
    @StringRes val text: Int,
    val fontColor: ColorType = ColorType.Normal,
    val backgroundColor: ColorType = ColorType.Normal,
    val onClick: (MutableState<String>, (String?) -> Unit) -> Unit = { it, _ ->
        it.value = it.value.trimSign(string(text))
    }
)

enum class ColorType {
    OnAccent, Accent, Normal,
}

val buttons = listOf(
    ButtonData(
        (R.string.clear), fontColor = ColorType.Accent
    ) { it, commit ->
        if (it.value.isNotEmpty()) {
            it.value = ""
        } else {
            commit(null)
        }
    },
    ButtonData(
        (R.string.backspace), fontColor = ColorType.Accent
    ) { it, _ ->
        it.value = it.value.run {
            if (isNotEmpty()) substring(0, length - 1)
            else this
        }
    },
    ButtonData(
        (R.string.percent), fontColor = ColorType.Accent
    ) { it, _ ->
        if ((it.value.isNotEmpty() && it.value[it.value.length - 1] in '0'..'9')) {
            it.value = it.value + '%'
        }
    },
    ButtonData(
        (R.string.divide), fontColor = ColorType.Accent
    ),
    ButtonData(
        (R.string.seven)
    ),
    ButtonData(
        (R.string.eight)
    ),
    ButtonData(
        (R.string.nine)
    ),
    ButtonData(
        (R.string.multiplication), fontColor = ColorType.Accent
    ),
    ButtonData(
        (R.string.four)
    ),
    ButtonData(
        (R.string.five)
    ),
    ButtonData(
        (R.string.six)
    ),
    ButtonData(
        (R.string.minus), fontColor = ColorType.Accent
    ),
    ButtonData(
        (R.string.one)
    ),
    ButtonData(
        (R.string.two)
    ),
    ButtonData(
        (R.string.three)
    ),
    ButtonData(
        (R.string.plus), fontColor = ColorType.Accent
    ),
    ButtonData(
        (R.string.brackets), fontColor = ColorType.Accent
    ) { it, _ ->
        if ((it.value.isNotEmpty() && it.value[it.value.length - 1].toString() in operator.plus("(")) || it.value.isEmpty()) {
            it.value = it.value + '('
            return@ButtonData
        }
        val (first, second) = it.value.countPair('(', ')')
        if (first > second && it.value[it.value.length - 1] != '(') {
            it.value = it.value + ")"
        }
    },
    ButtonData(
        (R.string.zero)
    ),
    ButtonData(
        (R.string.dot)
    ),
    ButtonData(
        (R.string.equal), backgroundColor = ColorType.Accent, fontColor = ColorType.OnAccent
    ) { it, commit ->
        val raw = it.value.trimSign("")
            .replace("(+", "(0+")
            .replace("(-", "(0+")
            .replace("(×", "(0÷")
            .replace("(÷", "(0×")
            .run {
                val (first, second) = countPair('(', ')')
                if (first > second) {
                    this + ")".repeat(first - second)
                } else this
            }
        if (raw.indexOfChars(char = charArrayOf('+', '-', '×', '÷', '%')) != null) {
            val result = CalculateUtils.calculate(raw).replace(",", "")
            commit("$raw=$result")
            it.value = result
        }
    },
)