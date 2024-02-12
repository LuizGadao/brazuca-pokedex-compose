package br.com.luizgadao.brazucapokedexcompose.pokemons.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luizgadao.brazucapokedexcompose.pokemons.data.network.request.NetworkDataSource
import br.com.luizgadao.brazucapokedexcompose.pokemons.data.network.request.PokemonResponse
import br.com.luizgadao.core.network.KtorClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {

    private val networkDataSource = NetworkDataSource(KtorClient())

    private val state = MutableStateFlow<PokemonListViewState>(
        value = PokemonListViewState.Loading
    )

    val stateFlow = state.asStateFlow()

    fun doRequest() = viewModelScope.launch {
        state.update {
            return@update PokemonListViewState.Loading
        }

        networkDataSource
            .getPokemons(offset = 0, limit = 50)
            .success { apiResponse ->
                Log.i("PokemonListApiResponse", apiResponse.results[0].name)

                launch {
                    val deferreds = apiResponse.results.map { pokemonResponse ->
                        async {
                            networkDataSource.getPokemonDetail(pokemonResponse.name)
                        }
                    }

                    val myPokemons = apiResponse.results.toMutableList()
                    deferreds
                        .awaitAll()
                        .mapIndexed { index, item ->
                            item.success {
                                myPokemons[index] = it
                            }
                        }

                    state.update {
                        return@update PokemonListViewState.Success(
                            pokemons = myPokemons  //apiResponse.results
                        )
                    }
                }

                /*state.update {
                    return@update PokemonListViewState.Success(
                        pokemons = apiResponse.results
                    )
                }*/
            }
    }
}