package com.example.herodetail.di

import com.example.hero_interactors.GetFromCache
import com.example.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {
    @Provides
    @Singleton
    fun provideGetHeroFromCache(heroInteractors: HeroInteractors): GetFromCache {
        return heroInteractors.getCachedHeroes
    }
}