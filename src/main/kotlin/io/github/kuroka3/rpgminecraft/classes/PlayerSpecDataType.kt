package io.github.kuroka3.rpgminecraft.classes

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

class PlayerSpecDataType : PersistentDataType<String, PlayerSpec> {
    override fun getPrimitiveType(): Class<String> {
        return String::class.java
    }

    override fun getComplexType(): Class<PlayerSpec> {
        return PlayerSpec::class.java
    }

    override fun fromPrimitive(p0: String, p1: PersistentDataAdapterContext): PlayerSpec {
        return Json.decodeFromString<PlayerSpec>(p0)
    }

    override fun toPrimitive(p0: PlayerSpec, p1: PersistentDataAdapterContext): String {
        return Json.encodeToString(p0)
    }
}