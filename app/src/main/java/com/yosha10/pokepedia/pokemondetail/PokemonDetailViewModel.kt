package com.yosha10.pokepedia.pokemondetail

import androidx.lifecycle.ViewModel
import com.yosha10.pokepedia.data.remote.responses.PokemonResponse
import com.yosha10.pokepedia.repository.PokePediaRepository
import com.yosha10.pokepedia.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokePediaRepository
) : ViewModel() {

    suspend fun getPokemonDetail(pokemonName: String): Resource<PokemonResponse> {
        return repository.getPokemonDetail(pokemonName)
    }
}