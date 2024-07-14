package io.github.kuroka3.rpgminecraft.classes

import io.github.kuroka3.rpgminecraft.RPGMinecraftPlugin
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player

class PlayerWithSpec(val p: Player) {
    val spec: PlayerSpec

    init {
        val key = NamespacedKey(RPGMinecraftPlugin.instance, "spec")
        val container = p.persistentDataContainer
        if(container.has(key, PlayerSpecDataType())) {
            spec = container.get(key, PlayerSpecDataType()) as PlayerSpec
        } else {
            spec = PlayerSpec(0.05f, 1.5f)
        }
    }

    fun save(sp: PlayerSpec = spec) {
        val key = NamespacedKey(RPGMinecraftPlugin.instance, "spec")
        p.persistentDataContainer.set(key, PlayerSpecDataType(), sp)
    }
}