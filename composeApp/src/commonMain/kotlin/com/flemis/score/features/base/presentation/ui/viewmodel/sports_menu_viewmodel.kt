package com.flemis.score.features.base.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flemis.score.features.base.domain.entities.SportType
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class SportsMenuViewModel : ViewModel(), KoinComponent {

    private val _state: MutableStateFlow<MutableList<HashMap<String, String>>> = MutableStateFlow(mutableListOf())

    val state: StateFlow<MutableList<HashMap<String, String>>> = _state.onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = mutableListOf()
        )
    private val _selectedSportState: MutableStateFlow<SportType?> = MutableStateFlow(SportType.entries.first())
    val selectedSportState = _selectedSportState.onStart { }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = SportType.entries.first()
    )

    suspend fun changeSport(newSportType: SportType) {
        _selectedSportState.update { currentState -> newSportType }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}