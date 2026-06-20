package com.test.magicalhaven.ui.state

import com.test.magicalhaven.domain.model.Creature

sealed class ShelterUiState {
    object Loading : ShelterUiState()
    data class Catalog(
        val creatures: List<Creature>,
    ) : ShelterUiState()
    data class Success(val message: String) : ShelterUiState()
    data class Error(val error: String) : ShelterUiState()
    data class Statistics(
        val total: Int,
        val adopted: Int,
        val popular: String?
    ) : ShelterUiState()
    data class MyCollection(val creatures: List<Creature>, val balance: Double) : ShelterUiState()
}
