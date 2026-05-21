package com.test.magicalhaven.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.test.magicalhaven.data.repository.CreatureRepository
import com.test.magicalhaven.ui.state.ShelterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        val creatures = repo.getAllCreatures()
        _uiState.value = ShelterUiState.Catalog(creatures)
    }

    fun attemptBinding(visitorName: String, budget: Double, creatureId: String) {
        val creature = repo.getCreatureById(creatureId)
        if (creature == null) {
            _uiState.value = ShelterUiState.Error("Creature not found")
            return
        }

        if (budget >= creature.dailyExpenses) {
            repo.removeCreatureById(creatureId)
            _uiState.value = ShelterUiState.Success("Contract signed for ${creature.name}!")
        } else {
            _uiState.value = ShelterUiState.Error("Insufficient budget: ${creature.dailyExpenses} gold required")
        }
    }

    fun showStatistics() {
        _uiState.value = ShelterUiState.Statistics(
            total = repo.getAvailableCount(),
            adopted = repo.getAdoptedCount(),
            popular = repo.getMostPopularSpecies()
        )
    }
}
