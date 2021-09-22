package com.example.herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.DataState
import com.example.hero_domain.exhaustive
import com.example.hero_interactors.GetFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val getHeroesFromCache: GetFromCache,
    private val stateSavedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())


    init {
        stateSavedStateHandle.get<Int>("heroId")?.let { heroId ->
            onTriggerEvent(HeroDetailEvent.GetHeroFromCache(heroId))
        }
    }

    fun onTriggerEvent(event: HeroDetailEvent) {
        when (event) {
            is HeroDetailEvent.GetHeroFromCache -> {
                getHeroFromCache(event.id)
            }
        }.exhaustive
    }

    private fun getHeroFromCache(id: Int) {
        getHeroesFromCache.execute(id).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Response -> {
                    //TODO: Handles Errors
                }
            }.exhaustive
        }.launchIn(viewModelScope)
    }
}