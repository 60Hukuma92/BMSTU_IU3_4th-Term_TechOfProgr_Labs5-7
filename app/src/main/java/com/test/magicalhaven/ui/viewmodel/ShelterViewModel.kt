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

    private var currentUsername: String = ""

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

    fun loginAndAdopt(visitorName: String, creatureId: String) {
        viewModelScope.launch {
            _uiState.value = ShelterUiState.Loading
            currentUsername = visitorName
            
            // 1. Авторизуемся (получаем JWT)
            val loggedIn = repo.login(visitorName, "password")
            if (loggedIn) {
                // 2. Проверяем информацию об игроке (баланс)
                val player = repo.getPlayerInfo()
                val creature = repo.getAllCreatures().find { it.id == creatureId }
                
                if (player != null && creature != null) {
                    if (player.balance >= creature.adoptionCost) {
                        // 3. Усыновляем
                        val success = repo.removeCreatureById(creatureId)
                        if (success) {
                            _uiState.value = ShelterUiState.Success("Successfully bound to \${creature.name}!")
                        } else {
                            _uiState.value = ShelterUiState.Error("Server rejected adoption. Maybe not enough gold?")
                        }
                    } else {
                        _uiState.value = ShelterUiState.Error("Insufficient balance: \${player.balance} gold. Need \${creature.adoptionCost} for \${creature.name}")
                    }
                } else {
                    _uiState.value = ShelterUiState.Error("Creature or Player info not found.")
                }
            } else {
                _uiState.value = ShelterUiState.Error("Authentication failed.")
            }
        }
    }

    fun showStatistics() {
        viewModelScope.launch {
            _uiState.value = ShelterUiState.Loading
            _uiState.value = ShelterUiState.Statistics(
                total = repo.getAvailableCount(),
                adopted = repo.getAdoptedCount(),
                popular = repo.getMostPopularSpecies()
            )
        }
    }

    fun showMyCreatures(visitorName: String) {
        viewModelScope.launch {
            _uiState.value = ShelterUiState.Loading
            repo.login(visitorName, "password") 
            val player = repo.getPlayerInfo()
            if (player != null) {
                _uiState.value = ShelterUiState.MyCollection(player.adoptedCreatures, player.balance)
            } else {
                _uiState.value = ShelterUiState.Error("Could not fetch player info for \${visitorName}")
            }
        }
    }
}
