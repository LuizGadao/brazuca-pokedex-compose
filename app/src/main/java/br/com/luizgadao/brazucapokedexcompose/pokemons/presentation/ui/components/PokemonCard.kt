package br.com.luizgadao.brazucapokedexcompose.pokemons.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.luizgadao.brazucapokedexcompose.pokemons.data.network.request.PokemonResponse
import br.com.luizgadao.brazucapokedexcompose.pokemons.data.network.request.Types
import br.com.luizgadao.brazucapokedexcompose.ui.theme.BrazucaPokedexComposeTheme
import coil.compose.SubcomposeAsyncImage

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemonResponse: PokemonResponse,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(pokemonResponse.getColor()))
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {

        Text(
            text = pokemonResponse.name,
            style = MaterialTheme.typography
                .titleLarge.copy(fontSize = 22.sp, color = Color.White)
        )

        PokemonImage(
            modifier = Modifier.size(120.dp)
                //.background(Color.Black)
                .align(Alignment.BottomCenter),
            url = pokemonResponse.img
        )
    }
}

@Composable
fun PokemonImage(
    modifier: Modifier = Modifier,
    url: String
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = "pokeomon image",
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentScale = ContentScale.Crop
    )
}

@Preview(showSystemUi = true)
@Composable
fun PokemonCardPreview(
) {
    val types = Types(Types.Type(name = "grass"))

    BrazucaPokedexComposeTheme {
        PokemonCard(
            modifier = Modifier
                .padding(12.dp)
                .width(200.dp)
                .height(180.dp),
            pokemonResponse = PokemonResponse(
                name = "Gadão",
                types = listOf(types)
            )
        )
    }
}