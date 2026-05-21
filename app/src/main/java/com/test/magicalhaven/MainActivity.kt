package com.test.magicalhaven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.test.magicalhaven.ui.screen.ShelterScreen
import com.test.magicalhaven.ui.viewmodel.ShelterViewModel
import javax.inject.Inject


class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: ShelterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MagicalHavenApp).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ShelterScreen(viewModel)
                }
            }
        }
    }
}