package br.com.luizgadao.brazucapokedexcompose.pokemons.data.network.request

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import br.com.luizgadao.brazucapokedexcompose.ui.theme.BrazucaPokedexComposeTheme
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListApiResponse(
    val count: Int,
    val results: List<PokemonResponse>
)

@Serializable
data class PokemonResponse(
    val name: String,
    val url: String? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val types: List<Types> = listOf(),
) {
    val id = url?.split("/")?.takeLast(2)?.get(0) ?: "0"
    val img =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"

    fun getColor(): Long {
        val first = types.firstOrNull() ?: return 0xFF58ABF6

        first.let {
            return when(it.type.name){
                "grass", "bug" -> 0xFF2CDAB1
                "fire" -> 0xFFF7786B
                "water", "fighting", "normal" -> 0xFF58ABF6
                "electric", "psychic" -> 0xFFFFCE4B
                "poison", "ghost" -> 0xFF9F5BBA
                "ground", "rock" -> 0xFFCA8179
                "dark" -> 0xFF303943
                else -> 0xFF58ABF6
            }
        }
    }
}

@Serializable
data class Types(
    val type: Type
) {
    @Serializable
    data class Type(
        val name: String
    )
}

