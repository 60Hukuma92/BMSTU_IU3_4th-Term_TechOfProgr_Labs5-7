package com.test.magicalhaven.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.test.magicalhaven.ui.state.ShelterUiState
import com.test.magicalhaven.ui.viewmodel.ShelterViewModel

@Composable
fun ShelterScreen(viewModel: ShelterViewModel) {
    val state by viewModel.uiState.collectAsState()
    var visitorName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "--- Magical Haven Shelter ---", style = MaterialTheme.typography.headlineMedium)

        TextField(
            value = visitorName, 
            onValueChange = { visitorName = it }, 
            label = { Text("Enter Your Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Button(onClick = { viewModel.showStatistics() }, modifier = Modifier.fillMaxWidth()) { Text("General Stats") }
            Button(
                onClick = { viewModel.showMyCreatures(visitorName) }, 
                modifier = Modifier.fillMaxWidth(),
                enabled = visitorName.isNotBlank()
            ) { Text("My Collection & Balance") }
        }

        HorizontalDivider(Modifier.padding(vertical = 12.dp))

        when (val s = state) {
            is ShelterUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ShelterUiState.Catalog -> {
                if (s.creatures.isEmpty()) {
                    Text(text = "All creatures are adopted! Good job.", color = Color.Gray)
                    Button(onClick = { viewModel.loadCatalog() }) { Text("Refresh") }
                } else {
                    s.creatures.forEach { creature ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(text = creature.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = "${creature.species} | Maint: ${creature.dailyExpenses} gold/day")
                            Button(
                                onClick = { viewModel.loginAndAdopt(visitorName, creature.id) },
                                enabled = visitorName.isNotBlank()
                            ) {
                                Text("Adopt for ${creature.adoptionCost} gold")
                            }
                        }
                    }
                }
            }

            is ShelterUiState.Success -> {
                Text(text = s.message, color = Color.Green, style = MaterialTheme.typography.headlineSmall)
                Button(onClick = { viewModel.loadCatalog() }) { Text("Back to Catalog") }
            }

            is ShelterUiState.Error -> {
                Text(text = "Error: ${s.error}", color = Color.Red)
                Button(onClick = { viewModel.loadCatalog() }) { Text("Retry") }
            }

            is ShelterUiState.Statistics -> {
                Text("Total creatures: ${s.total}")
                Text("Adopted so far: ${s.adopted}")
                Text("Most popular species: ${s.popular ?: "None"}")
                Button(onClick = { viewModel.loadCatalog() }) { Text("Back") }
            }

            is ShelterUiState.MyCollection -> {
                Text(text = "Player: $visitorName", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Current Balance: ${s.balance} gold", color = Color(0xFFDAA520))
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                Text("Your Magical Creatures:", style = MaterialTheme.typography.titleLarge)
                if (s.creatures.isEmpty()) {
                    Text("Empty... Go adopt someone!")
                } else {
                    s.creatures.forEach { creature ->
                        Text(text = "• ${creature.name} (${creature.species})")
                    }
                }
                Button(
                    onClick = { viewModel.loadCatalog() }, 
                    modifier = Modifier.padding(top = 16.dp)
                ) { Text("Back to Shelter") }
            }
        }
    }
}
