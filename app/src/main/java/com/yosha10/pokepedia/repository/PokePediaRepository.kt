package com.yosha10.pokepedia.repository

import com.yosha10.pokepedia.data.remote.ApiService
import com.yosha10.pokepedia.data.remote.responses.PokemonListResponse
import com.yosha10.pokepedia.data.remote.responses.PokemonResponse
import com.yosha10.pokepedia.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokePediaRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonListResponse> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonDetail(name: String): Resource<PokemonResponse> {
        val response = try {
            api.getPokemonDetail(name)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}