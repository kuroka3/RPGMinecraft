package io.github.kuroka3.rpgminecraft.classes

import io.github.kuroka3.rpgminecraft.RPGMinecraftPlugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

class Artifact {
    enum class ArtifactType {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS
    }

    enum class ArtifactClass {
        NETHERITE,
        DIAMOND,
        GOLDEN,
        IRON
    }

    val type: ArtifactType
    val cl: ArtifactClass
    val mainOption: Option?
    val subOptions: Array<Option>

    constructor(type: ArtifactType, cl: ArtifactClass, mainOption: Option?, subOptions: Array<Option>) {
        this.type = type
        this.cl = cl
        this.mainOption = mainOption
        this.subOptions = subOptions
    }

    constructor(item: ItemStack) {
        val mainKey = NamespacedKey(RPGMinecraftPlugin.instance, "main_option")

        val types = item.type.name.split("_")
        type = ArtifactType.valueOf(types[1])
        cl = ArtifactClass.valueOf(types[0])

        val container = item.itemMeta.persistentDataContainer
        if (container.has(mainKey, OptionDataType())) {
            val value = container.get(mainKey, OptionDataType())
            mainOption = value as Option
        } else {
            mainOption = null
        }

        val subOp = mutableListOf<Option>()

        var index = -1
        while (true) {
            index++

            val subKey = NamespacedKey(RPGMinecraftPlugin.instance, "sub_option_$index")
            if (container.has(subKey, OptionDataType())) {
                val value = container.get(subKey, OptionDataType())
                subOp.add(value as Option)
            } else break
        }

        subOptions = subOp.toTypedArray()
    }

    val itemStack: ItemStack
        get() {
            val mainKey = NamespacedKey(RPGMinecraftPlugin.instance, "main_option")

            val loreList = mutableListOf<TextComponent>()
            val item = ItemStack(Material.valueOf("${cl.name}_${type.name}"))
            val meta = item.itemMeta

            if (mainOption != null) {
                meta.persistentDataContainer.set(mainKey, OptionDataType(), mainOption)
                loreList.add(mainOption.boldLore)
            }

            loreList.add(Component.text("---------------").color(TextColor.color(0x00aaaaaa)).decoration(TextDecoration.ITALIC, false))

            subOptions.forEachIndexed { index, subOption ->
                val subKey = NamespacedKey(RPGMinecraftPlugin.instance, "sub_option_$index")
                meta.persistentDataContainer.set(subKey, OptionDataType(), subOption)
                loreList.add(subOption.lore)
            }
            meta.lore(loreList)
            item.setItemMeta(meta)

            return item
        }

    class Builder(val type: ArtifactType, val cl: ArtifactClass) {
        var mainOption: Option? = null
        var subOptions: Array<Option> = emptyArray()

        fun setMainOption(option: Option): Builder {
            this.mainOption = option
            return this
        }

        fun setSubOptions(subOptions: Array<Option>): Builder {
            this.subOptions = subOptions
            return this
        }

        fun addSubOption(option: Option): Builder {
            this.subOptions += option
            return this
        }

        fun removeSubOption(index: Int): Builder {
            this.subOptions = this.subOptions.sliceArray(0 until index) + this.subOptions.sliceArray(index + 1 until this.subOptions.size - 1)
            return this
        }
        fun removeSubOption(option: Option): Builder {
            this.subOptions = this.subOptions.filter { it != option }.toTypedArray()
            return this
        }

        fun build(): Artifact {
            return Artifact(type, cl, mainOption, subOptions)
        }
    }
}