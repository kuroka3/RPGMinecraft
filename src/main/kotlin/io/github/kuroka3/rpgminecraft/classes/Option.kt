package io.github.kuroka3.rpgminecraft.classes

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@Serializable
data class Option(val optionType: OptionType, val value: Float) {
    enum class OptionType(val korean: String) {
        CRITICAL_PERCENT("크리티컬"),
        CRITICAL_MAGNIFICATION("크리티컬 피해"),
        ATTACK_MAGNIFICATION("공격력"),
        ATTACK_ADD("공격력"),
        DEFEND_MAGNIFICATION("방어력"),
        DEFEND_ADD("방어력"),
        HP_MAGNIFICATION("생명력"),
        HP_ADD("생명력")
    }

    val lore: TextComponent
        get() = toLore()

    val boldLore: TextComponent
        get() = toLore(true)

    private fun toLore(bold: Boolean = false) = Component.text(this.optionType.korean).color(TextColor.color(0x00ffffff)).decoration(TextDecoration.BOLD, bold).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        .append(Component.text(" : ").color(TextColor.color(0x00aaaaaa)).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE))
        .append(Component.text("${this.value*100}%").color(TextColor.color(0x0055FF55)).decoration(TextDecoration.BOLD, bold).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE))
}