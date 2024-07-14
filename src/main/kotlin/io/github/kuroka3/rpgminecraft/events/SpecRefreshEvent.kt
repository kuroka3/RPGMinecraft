package io.github.kuroka3.rpgminecraft.events

import io.github.kuroka3.rpgminecraft.classes.Artifact
import io.github.kuroka3.rpgminecraft.classes.Option
import io.github.kuroka3.rpgminecraft.classes.PlayerSpec
import io.github.kuroka3.rpgminecraft.classes.PlayerWithSpec
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class SpecRefreshEvent : Listener {
    @EventHandler
    fun onPlayerRefreshSpec(e: InventoryClickEvent) {
        val p = e.whoClicked
        if (p is Player) {
            PlayerWithSpec(p).save(loadSpec(p))
            p.sendMessage("Saved! ${PlayerWithSpec(p).spec.toString()}");
        }
    }

    fun loadSpec(p: Player): PlayerSpec {
        var spec = PlayerSpec(0.05f, 1.5f)
        val artifacts = mutableListOf<Artifact>()
        p.inventory.helmet?.let { artifacts.add(Artifact(it)) }
        p.inventory.chestplate?.let { artifacts.add(Artifact(it)) }
        p.inventory.leggings?.let { artifacts.add(Artifact(it)) }
        p.inventory.boots?.let { artifacts.add(Artifact(it)) }

        artifacts.forEach { artifact ->
            val mainOp = artifact.mainOption
            if (mainOp != null) {
                if (mainOp.optionType == Option.OptionType.CRITICAL_PERCENT) {
                    spec.crit_per += mainOp.value
                } else if (mainOp.optionType == Option.OptionType.CRITICAL_MAGNIFICATION) {
                    spec.crit_mag += mainOp.value
                }
            }

            val subOps = artifact.subOptions
            subOps.forEach { subOp ->
                if (subOp.optionType == Option.OptionType.CRITICAL_PERCENT) {
                    spec.crit_per += subOp.value
                } else if (subOp.optionType == Option.OptionType.CRITICAL_MAGNIFICATION) {
                    spec.crit_mag += subOp.value
                }
            }
        }

        return spec
    }
}