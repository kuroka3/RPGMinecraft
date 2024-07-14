package io.github.kuroka3.rpgminecraft.events

import io.github.kuroka3.rpgminecraft.classes.Artifact
import io.github.kuroka3.rpgminecraft.classes.Option
import io.github.kuroka3.rpgminecraft.classes.PlayerWithSpec
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffectType

class CriticalEvent : Listener {
    @EventHandler
    fun onCritical(e: EntityDamageByEntityEvent){
        val p = e.damager
        if (p is Player) {
            if (isCritical(p)) {
                val spec = PlayerWithSpec(p).spec
                if (Math.random() < spec.crit_per) {
                    e.damage *= spec.crit_mag
                    p.sendMessage("Critical!")
                    return
                }
            }
            p.sendMessage("No Critical")
        }
    }

    private fun isCritical(p: Player): Boolean {
        return p.fallDistance > 0.0F && !(p as LivingEntity).isOnGround && !p.hasPotionEffect(PotionEffectType.BLINDNESS) && p.vehicle == null
    }
}