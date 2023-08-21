package com.yosha10.pokepedia.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.yosha10.pokepedia.data.models.PokemonList
import com.yosha10.pokepedia.repository.PokePediaRepository
import com.yosha10.pokepedia.utils.Constants.PAGE_SIZE
import com.yosha10.pokepedia.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokePediaRepository: PokePediaRepository
) : ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokemonList>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokemonList>()
    private var isSearchStarted = true
    var isSearching = mutableStateOf(false)

    init {
        loadPaginatedData()
    }

    fun searchData(query: String){
        val listToSearch = if (isSearchStarted) {
            pokemonList.value
        } else {
            cachedPokemonList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearchStarted = true
                isSearching.value = false
                return@launch
            }
            val result = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if (isSearchStarted) {
                cachedPokemonList = pokemonList.value
                isSearchStarted = false
            }
            pokemonList.value = result
            isSearching.value = true
        }
    }

    fun loadPaginatedData() {
        viewModelScope.launch {
            isLoading.value = true
            val result = pokePediaRepository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data?.count!!
                    val pokePediaEntries = result.data.results?.mapIndexed { index, entry ->
                        val number = if (entry?.url?.endsWith("/") == true) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry?.url?.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokemonList(
                            entry?.name?.capitalize(Locale.ROOT) ?: "",
                            url,
                            number?.toInt() ?: 0
                        )
                    }
                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokePediaEntries ?: listOf()
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
        }
    }


    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { pallete ->
            pallete?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

}