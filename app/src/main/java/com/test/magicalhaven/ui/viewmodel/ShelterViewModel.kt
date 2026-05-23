package com.test.magicalhaven.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.magicalhaven.data.repository.CreatureRepository
import com.test.magicalhaven.ui.state.ShelterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShelterViewModel @Inject constructor(
    private val repo: CreatureRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ShelterUiState>(ShelterUiState.Loading)
    val uiState: StateFlow<ShelterUiState> = _uiState.asStateFlow()

    init {
        loadCatalog()
    }

    fun loadCatalog() {
        viewModelScope.launch {
            _uiState.value = ShelterUiState.Loading
            val creatures = repo.getAllCreatures()
            _uiState.value = ShelterUiState.Catalog(creatures)
        }
    }

    fun attemptBinding(visitorName: String, budget: Double, creatureId: String) {
        viewModelScope.launch {
            val creature = repo.getCreatureById(creatureId)
            if (creature == null) {
                _uiState.value = ShelterUiState.Error("Creature not found")
                return@launch
            }

            if (budget >= creature.dailyExpenses) {
                val success = repo.removeCreatureById(creatureId)
                if (success) {
                    _uiState.value = ShelterUiState.Success("Contract signed for ${creature.name}!")
                } else {
                    _uiState.value = ShelterUiState.Error("Failed to adopt creature on server")
                }
            } else {
                _uiState.value = ShelterUiState.Error("Insufficient budget: ${creature.dailyExpenses} gold required")
            }
        }
    }

    fun showStatistics() {
        viewModelScope.launch {
            _uiState.value = ShelterUiState.Loading
            val total = repo.getAvailableCount()
            val adopted = repo.getAdoptedCount()
            val popular = repo.getMostPopularSpecies()
            _uiState.value = ShelterUiState.Statistics(
                total = total,
                adopted = adopted,
                popular = popular
            )
        }
    }
}
