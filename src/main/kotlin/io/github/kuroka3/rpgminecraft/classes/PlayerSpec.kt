package io.github.kuroka3.rpgminecraft.classes

import kotlinx.serialization.Serializable

@Serializable
data class PlayerSpec(var crit_per: Float, var crit_mag: Float) {
}