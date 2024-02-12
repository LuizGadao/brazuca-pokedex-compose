package br.com.luizgadao.brazucapokedexcompose.pokemons.data.network.request

import br.com.luizgadao.core.network.KtorClient
import br.com.luizgadao.core.network.NetworkResult
import io.ktor.client.call.body
import io.ktor.client.request.get

class NetworkDataSource(
    private val networkClient: KtorClient
) {

    suspend fun getPokemons(
        offset: Int?,
        limit: Int
    ): NetworkResult<PokemonListApiResponse> {

        return networkClient
            .request {
                networkClient
                    .httpClient
                    .get("pokemon?offset=$offset&limit=$limit")
                    .body<PokemonListApiResponse>()
            }
    }

    suspend fun getPokemonDetail(
        idOrName: String
    ): NetworkResult<PokemonResponse> {
        return networkClient
            .request {
                networkClient
                    .httpClient
                    .get("pokemon/$idOrName")
                    .body()
            }
    }

}