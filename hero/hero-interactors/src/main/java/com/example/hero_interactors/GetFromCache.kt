package com.example.hero_interactors

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UIComponent
import com.example.hero_datasource.cache.HeroCacheService
import com.example.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.xml.crypto.Data

class GetFromCache(
    private val cache: HeroCacheService
) {
    fun execute(id: Int): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading<Hero>(progressBarState = ProgressBarState.Loading))

            val cachedHero =
                cache.getHero(id) ?: throw Exception("This hero doesn't exist in the database")

            emit(DataState.Data<Hero>(cachedHero))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<Hero>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading<Hero>(progressBarState = ProgressBarState.Idle))
        }
    }
}