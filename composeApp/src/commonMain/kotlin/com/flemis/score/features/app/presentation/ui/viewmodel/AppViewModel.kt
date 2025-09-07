package com.flemis.score.features.app.presentation.ui.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.coringa
import score.composeapp.generated.resources.home
import score.composeapp.generated.resources.teams

class AppViewModel : ViewModel(), KoinComponent {

    val homeMenuItemsState: MutableStateFlow<List<MutableMap<String, Any>>>
    private var _selectedBottomNavIndexState: MutableStateFlow<Int> = MutableStateFlow(0)
    var selectedIndexState: StateFlow<Int> =
        _selectedBottomNavIndexState.onStart { }.stateIn(viewModelScope, SharingStarted.Lazily, 0)


    init {

        homeMenuItemsState = MutableStateFlow<List<MutableMap<String, Any>>>(
            listOf(
                mutableMapOf<String, Any>(
                    "screen" to "home",
                    "icon" to Icons.Filled.Home,
                    "label" to Res.string.home
                ),
                mutableMapOf<String, Any>(
                    "screen" to "teams",
                    "icon" to Res.drawable.coringa,
                    "label" to Res.string.teams
                )
            )
        )
    }

    fun selectIndex(index: Int) {
        viewModelScope.launch {
            _selectedBottomNavIndexState.value = index
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()

    }
}