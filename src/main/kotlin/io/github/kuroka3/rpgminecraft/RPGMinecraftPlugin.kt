package io.github.kuroka3.rpgminecraft

import io.github.kuroka3.rpgminecraft.classes.Artifact
import io.github.kuroka3.rpgminecraft.classes.Option
import io.github.kuroka3.rpgminecraft.classes.OptionDataType
import io.github.kuroka3.rpgminecraft.commands.BuildArtifact
import io.github.kuroka3.rpgminecraft.events.CriticalEvent
import io.github.kuroka3.rpgminecraft.events.SpecRefreshEvent
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

public class RPGMinecraftPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: RPGMinecraftPlugin
    }

    override fun onEnable() {
        logger.info("Les go!")
        instance = this

        server.pluginManager.registerEvents(CriticalEvent(), this)
        server.pluginManager.registerEvents(SpecRefreshEvent(), this)

        getCommand("artifactbuilder")?.setExecutor(BuildArtifact())
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("whatisthis", true) && sender is Player) {
            val player = sender as Player

            val item = player.inventory.itemInMainHand
            val key = NamespacedKey(this@RPGMinecraftPlugin, "main_option")
            val container = item.itemMeta.persistentDataContainer
            if (container.has(key, OptionDataType())) {
                val value = container.get(key, OptionDataType())
                player.sendMessage("${value!!.optionType}: ${value.value}")
            }
        }
        return false
    }
}