package com.example.hero_interactors

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UIComponent
import com.example.core.util.Logger
import com.example.hero_datasource.cache.HeroCacheService
import com.example.hero_datasource.network.HeroService
import com.example.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetHeros(
    private val service: HeroService,
    private val cache: HeroCacheService
    //TODO: Add caching
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            //Emit the loading status
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Loading))

            // call to the api service
            val heros: List<Hero> = try {
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()

                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown Error"
                        )
                    )
                )

                // return an empty list
                listOf()
            }
            //TODO: caching

            cache.insertAll(heros)

            val cachedHeros = cache.selectAll()

            // emit an empty or full list
            emit(DataState.Data(cachedHeros))

        } catch (e: Exception) {
            e.printStackTrace()
            /*
            Emit to the viewModel
             */
            emit(
                DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        } finally {
            //loading the progressBar
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}