package com.example.herodetail.ui

sealed class HeroDetailEvent {
    data class GetHeroFromCache(
        val id: Int
    ) : HeroDetailEvent()
}