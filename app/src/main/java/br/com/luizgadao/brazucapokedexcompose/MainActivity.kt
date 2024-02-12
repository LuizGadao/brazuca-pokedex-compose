package br.com.luizgadao.brazucapokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.luizgadao.brazucapokedexcompose.pokemons.presentation.PokemonListViewModel
import br.com.luizgadao.brazucapokedexcompose.pokemons.presentation.PokemonListViewState
import br.com.luizgadao.brazucapokedexcompose.pokemons.presentation.PokemonListViewState.Error
import br.com.luizgadao.brazucapokedexcompose.pokemons.presentation.ui.components.PokemonCard
import br.com.luizgadao.brazucapokedexcompose.ui.theme.BrazucaPokedexComposeTheme
import br.com.luizgadao.core.network.KtorClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<PokemonListViewModel>()


        setContent {

            val state by viewModel.stateFlow.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                viewModel.doRequest()
            }

            BrazucaPokedexComposeTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    when (val viewState = state) {
                        PokemonListViewState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(all = 128.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        is Error -> {
                            Text(
                                text = "${viewState.message}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        is PokemonListViewState.Success -> {
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(viewState.pokemons) {
                                    PokemonCard(pokemonResponse = it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrazucaPokedexComposeTheme {
        Greeting("Android")
    }
}