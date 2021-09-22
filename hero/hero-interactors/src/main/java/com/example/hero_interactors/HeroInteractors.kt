package com.example.hero_interactors

import com.example.hero_datasource.cache.HeroCacheService
import com.example.hero_datasource.cache.HeroDatabase
import com.example.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

//all use cased holder
data class HeroInteractors(
    val getHeros: GetHeros,
    val getCachedHeroes: GetFromCache
) {
    companion object Factory {

        fun build(sqlDriver: SqlDriver): HeroInteractors {
            val service = HeroService.build()
            val cache = HeroCacheService.build(sqlDriver)

            return HeroInteractors(
                getHeros = GetHeros(service, cache),
                getCachedHeroes = GetFromCache(cache)
            )
        }

        val schema: SqlDriver.Schema = HeroDatabase.Schema
        val dbName: String = "heros.db"
    }

}