package br.com.luizgadao.brazucapokedexcompose.pokemons.presentation

import br.com.luizgadao.brazucapokedexcompose.pokemons.data.network.request.PokemonResponse

sealed interface PokemonListViewState {

    object Loading : PokemonListViewState
    data class Error(val message: String) : PokemonListViewState
    data class Success(
        val pokemons: List<PokemonResponse>
    ) : PokemonListViewState

}