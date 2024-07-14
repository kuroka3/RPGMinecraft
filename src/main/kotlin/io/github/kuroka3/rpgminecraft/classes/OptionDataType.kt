package io.github.kuroka3.rpgminecraft.classes

import com.sun.jna.StringArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

class OptionDataType : PersistentDataType<String, Option> {
    override fun getPrimitiveType(): Class<String> {
        return String::class.java
    }

    override fun getComplexType(): Class<Option> {
        return Option::class.java
    }

    override fun fromPrimitive(p0: String, p1: PersistentDataAdapterContext): Option {
        return Json.decodeFromString<Option>(p0)
    }

    override fun toPrimitive(p0: Option, p1: PersistentDataAdapterContext): String {
        return Json.encodeToString(p0)
    }
}