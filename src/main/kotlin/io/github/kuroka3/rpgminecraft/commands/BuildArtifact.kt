package io.github.kuroka3.rpgminecraft.commands

import io.github.kuroka3.rpgminecraft.classes.Artifact
import io.github.kuroka3.rpgminecraft.classes.Option
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

class BuildArtifact : CommandExecutor {

    val builderMap: MutableMap<UUID, Artifact.Builder> = mutableMapOf()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be executed by players")
            return false
        }

        val p = sender as Player

        if (args[0] == "create") {
            val type = Artifact.ArtifactType.valueOf(args[2])
            val cl = Artifact.ArtifactClass.valueOf(args[1])
            builderMap[p.uniqueId] = Artifact.Builder(type, cl)
            printValues(p)
        }

        if (args[0] == "setMainOption") {
            val option = Option(Option.OptionType.valueOf(args[1]), args[2].toFloat())
            builderMap[p.uniqueId]!!.setMainOption(option)
            printValues(p)
        }

        if (args[0] == "addSubOption") {
            val option = Option(Option.OptionType.valueOf(args[1]), args[2].toFloat())
            builderMap[p.uniqueId]!!.addSubOption(option)
            printValues(p)
        }

        if (args[0] == "removeSubOption") {
            val option = Option(Option.OptionType.valueOf(args[1]), args[2].toFloat())
            builderMap[p.uniqueId]!!.removeSubOption(option)
            printValues(p)
        }

        if (args[0] == "build") {
            p.inventory.addItem(builderMap[p.uniqueId]!!.build().itemStack)
        }

        return false
    }

    private fun printValues(p: Player) {
        val item = builderMap[p.uniqueId]!!.build().itemStack
        p.sendMessage(item.type.toString())
        item.itemMeta.lore()!!.forEach { el ->
            p.sendMessage(el)
        }
    }
}