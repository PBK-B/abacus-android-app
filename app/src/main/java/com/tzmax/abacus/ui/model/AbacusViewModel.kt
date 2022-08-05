package com.tzmax.abacus.ui.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AbacusViewModel : ViewModel() {
    val formula = mutableStateOf("")
    val history = mutableStateListOf<String>()
}