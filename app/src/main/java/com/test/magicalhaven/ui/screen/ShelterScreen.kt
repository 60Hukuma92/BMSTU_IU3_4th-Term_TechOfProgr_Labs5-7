package com.test.magicalhaven.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.test.magicalhaven.ui.state.ShelterUiState
import com.test.magicalhaven.ui.viewmodel.ShelterViewModel

@Composable
fun ShelterScreen(viewModel: ShelterViewModel) {
    val state by viewModel.uiState.collectAsState()
    var visitorName by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Text(text = "--- Magical Haven Shelter ---", style = MaterialTheme.typography.headlineMedium)

        TextField(value = visitorName, onValueChange = { visitorName = it }, label = { Text("Visitor Name") })
        TextField(value = budget, onValueChange = { budget = it }, label = { Text("Monthly Budget") })

        Button(onClick = { viewModel.showStatistics() }) { Text("Stats") }

        HorizontalDivider(
            Modifier.padding(vertical = 8.dp),
            DividerDefaults.Thickness,
            DividerDefaults.color
        )

        when (val s = state) {
            is ShelterUiState.Loading -> {
                Text("Loading magical creatures...")
            }
            is ShelterUiState.Catalog -> {
                s.creatures.forEach { creature ->
                    Text(text = "${creature.id}: ${creature.name} (${creature.species}) - ${creature.dailyExpenses} gold/day")
                    Button(onClick = { viewModel.attemptBinding(visitorName, budget.toDoubleOrNull() ?: 0.0, creature.id) }) {
                        Text("Bind to ${creature.id}")
                    }
                }
            }
            is ShelterUiState.Success -> {
                Text(text = "SUCCESS: ${s.message}", color = Color.Green)
                Button(onClick = { viewModel.loadCatalog() }) { Text("Back to Catalog") }
            }
            is ShelterUiState.Error -> {
                Text(text = "REJECTED: $s", color = Color.Red)
                Button(onClick = { viewModel.loadCatalog() }) { Text("Back") }
            }
            is ShelterUiState.Statistics -> {
                Text("Total in shelter: ${s.total}")
                Text("Already bound: ${s.adopted}")
                Text("Popular species: ${s.popular}")
                Button(onClick = { viewModel.loadCatalog() }) { Text("Back") }
            }
        }
    }
}