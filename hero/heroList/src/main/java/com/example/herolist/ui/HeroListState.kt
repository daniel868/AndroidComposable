package com.example.herolist.ui

import com.example.core.domain.ProgressBarState
import com.example.hero_domain.Hero


data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf()
) {

}